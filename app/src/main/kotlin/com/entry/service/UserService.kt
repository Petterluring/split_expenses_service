package com.entry.service

import com.entry.dto.generic.MessageResponseDto
import com.entry.dto.user.CreateUserRequestDto
import com.entry.dto.user.DeleteUserRequestDto
import com.entry.exception.DatabaseException
import com.entry.exception.InvalidRequestException
import com.entry.exception.ResourceAlreadyExistsException
import com.entry.model.user.User
import com.entry.policy.Policy
import com.entry.repository.UserRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service

@Service
class UserService(
    @param:Qualifier("passwordPolicy")
    private val passwordPolicy: Policy<String>,
    @param:Qualifier("usernamePolicy")
    private val usernamePolicy: Policy<String>,
    private val userRepository: UserRepository,
) {
    fun create(createUserRequest: CreateUserRequestDto): MessageResponseDto {
        blankExceptionCheck(createUserRequest.username, createUserRequest.password)
        policyExceptionCheck(createUserRequest.username, createUserRequest.password)

        if (userExists(createUserRequest.username)) {
            throw ResourceAlreadyExistsException("User ${createUserRequest.username} already exists")
        }

        val hashedPassword = createUserRequest.password // Hash password

        try {
            userRepository.save(
                User(
                    username = createUserRequest.username,
                    hashedPassword = hashedPassword,
                ),
            )
        } catch (e: DataAccessException) {
            throw DatabaseException("Failed to save user ${createUserRequest.username} to database: ${e.message}")
        }

        return MessageResponseDto(
            message = "User ${createUserRequest.username} created",
        )
    }

    fun delete(deleteUserRequest: DeleteUserRequestDto): MessageResponseDto {
        blankExceptionCheck(deleteUserRequest.username, deleteUserRequest.password)

        val hashedPassword = deleteUserRequest.password // Hash password

        var deletedUsers: Long
        try {
            deletedUsers =
                userRepository.deleteByUsernameAndPassword(
                    username = deleteUserRequest.username,
                    hashedPassword = hashedPassword,
                )
        } catch (e: DataAccessException) {
            throw DatabaseException("Failed to delete user ${deleteUserRequest.username} from database: ${e.message}")
        }

        return if (deletedUsers >= 1L) {
            MessageResponseDto(
                message = "User ${deleteUserRequest.username} deleted",
            )
        } else {
            throw InvalidRequestException("User ${deleteUserRequest.username} is non-existent or has the incorrect password")
        }
    }

    private fun blankExceptionCheck(
        username: String,
        password: String,
    ) {
        if (username.isBlank() || password.isBlank()) {
            throw InvalidRequestException("Username or password is missing")
        }
    }

    private fun policyExceptionCheck(
        username: String,
        password: String,
    ) {
        if (!passwordPolicy.matches(password) || !usernamePolicy.matches(username)) {
            val policies = "$passwordPolicy\n$usernamePolicy"
            throw InvalidRequestException(
                "Username or password is invalid. These must comply with the following character policies:\n$policies",
            )
        }
    }

    private fun userExists(username: String): Boolean {
        try {
            return userRepository.existsByUsername(username)
        } catch (e: DataAccessException) {
            throw DatabaseException("Unable to check if username exists: ${e.message}")
        }
    }
}
