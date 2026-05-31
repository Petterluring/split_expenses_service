package com.entry.repositories

import com.entry.model.user.User
import com.entry.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DataMongoTest
class UserRepositoryTest : TestcontainerResources {
    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
    }

    @Test
    fun `can check if user exists`() {
        val user =
            User(
                username = "cat",
                hashedPassword = "cat_password",
            )

        userRepository.save(user)

        val exists = userRepository.existsByUsername("cat")

        assertTrue { exists }
    }

    @Test
    fun `can delete user`() {
        val user =
            User(
                username = "cat",
                hashedPassword = "cat_password",
            )

        userRepository.save(user)

        var deletedUsers =
            userRepository.deleteByUsernameAndHashedPassword(
                username = "incorrect username",
                hashedPassword = "cat_password",
            )
        assertEquals(deletedUsers, 0L)

        deletedUsers =
            userRepository.deleteByUsernameAndHashedPassword(
                username = "cat",
                hashedPassword = "incorrect password",
            )
        assertEquals(deletedUsers, 0L)

        deletedUsers =
            userRepository.deleteByUsernameAndHashedPassword(
                username = "cat",
                hashedPassword = "cat_password",
            )
        assertEquals(deletedUsers, 1L)
    }
}
