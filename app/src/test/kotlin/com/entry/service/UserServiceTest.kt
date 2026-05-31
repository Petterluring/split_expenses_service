package com.entry.service

import com.entry.dto.user.CreateUserRequestDto
import com.entry.dto.user.DeleteUserRequestDto
import com.entry.exception.InvalidRequestException
import com.entry.exception.ResourceAlreadyExistsException
import com.entry.policy.Policy
import com.entry.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock
    lateinit var userRepository: UserRepository

    lateinit var userService: UserService

    @Test
    fun `can identify blank passwords and usernames`() {
        userService = buildUserService(matchBoolUsername = true, matchBoolPassword = true)
        val blankUsername =
            CreateUserRequestDto(
                username = "",
                password = "cat123",
            )
        val blankPassword =
            CreateUserRequestDto(
                username = "cat",
                password = "",
            )
        var error =
            assertThrows<InvalidRequestException> {
                userService.create(blankUsername)
            }
        assertEquals(error.message, "Username or password is missing")

        error =
            assertThrows<InvalidRequestException> {
                userService.create(blankPassword)
            }
        assertEquals(error.message, "Username or password is missing")
    }

    @Test
    fun `can identify if username and password follow character policies`() {
        val createUserRequest =
            CreateUserRequestDto(
                "cat",
                password = "cat123",
            )
        val boolPairs = listOf(Pair(false, false), Pair(true, false), Pair(false, true))
        for (p in boolPairs) {
            val usernameBool = p.first
            val passwordBool = p.second

            val userService = buildUserService(matchBoolUsername = usernameBool, matchBoolPassword = passwordBool)

            val error =
                assertThrows<InvalidRequestException> {
                    userService.create(createUserRequest)
                }
            assertTrue { error.message!!.contains("Username or password is invalid") }
        }
    }

    @Test
    fun `can check if user exists`() {
        val userService = buildUserService(matchBoolUsername = true, matchBoolPassword = true)

        val userCreateRequest =
            CreateUserRequestDto(
                username = "cat",
                password = "cat_password",
            )

        given(userRepository.existsByUsername("cat"))
            .willReturn(true)

        val error = assertThrows<ResourceAlreadyExistsException> { userService.create(userCreateRequest) }
        assertTrue(error.message!!.contains("already exists"))
    }

    @Test
    fun `can create user`() {
        val userService = buildUserService(matchBoolUsername = true, matchBoolPassword = true)

        val userCreateRequest =
            CreateUserRequestDto(
                username = "cat",
                password = "cat_password",
            )

        given(userRepository.existsByUsername("cat"))
            .willReturn(false)

        val result = userService.create(userCreateRequest)

        assertEquals(result.message, "User ${userCreateRequest.username} created")
    }

    @Test
    fun `can delete user`() {
        val userService = buildUserService(matchBoolUsername = true, matchBoolPassword = true)

        given(
            userRepository.deleteByUsernameAndPassword(
                username = "cat",
                hashedPassword = "cat_password",
            ),
        ).willReturn(1L)

        val deleteUserRequest =
            DeleteUserRequestDto(
                username = "cat",
                password = "cat_password",
            )

        val result = userService.delete(deleteUserRequest)

        assertEquals(result.message, "User ${deleteUserRequest.username} deleted")
    }

    @Test
    fun `can identify if username is non-existent or if password is incorrect`() {
        val userService = buildUserService(matchBoolUsername = true, matchBoolPassword = true)

        given(
            userRepository.deleteByUsernameAndPassword(
                username = "cat",
                hashedPassword = "cat_password",
            ),
        ).willReturn(0L)

        val deleteUserRequest =
            DeleteUserRequestDto(
                username = "cat",
                password = "cat_password",
            )

        val error =
            assertThrows<InvalidRequestException> {
                userService.delete(deleteUserRequest)
            }

        assertTrue(error.message!!.contains("is non-existent"))
        assertTrue(error.message!!.contains("incorrect password"))
    }

    private fun buildUserService(
        matchBoolUsername: Boolean,
        matchBoolPassword: Boolean,
    ): UserService =
        UserService(
            usernamePolicy = CharacterPolicyMock(matchBoolUsername),
            passwordPolicy = CharacterPolicyMock(matchBoolPassword),
            userRepository = userRepository,
        )
}

private class CharacterPolicyMock(
    var matchBool: Boolean,
) : Policy<String> {
    override fun matches(input: String): Boolean = matchBool
}
