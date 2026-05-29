package com.entry.repositories

/*
import com.entry.model.user.User
import com.entry.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.assertTrue


@DataMongoTest
class UserRepositoryTest : TestcontainerResources() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var environment: Environment

    /*
    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
    }
    */

    @Test
    fun test() {
        println("Spring sees Mongo URI = ${environment.getProperty("spring.data.mongodb.uri")}")
    }

    /*
    @Test
    fun `can check if user exists`() {
        val user = User(
            username = "cat",
            hashedPassword = "cat_password"
        )

        userRepository.save(user)

        val exists = userRepository.existsByUsername("cat")

        assertTrue { exists }
    }
    */

}
*/