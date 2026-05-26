package com.entry.service

import com.entry.characterpolicy.CharacterPolicy
import com.entry.dto.generic.MessageResponseDto
import com.entry.dto.user.CreateUserRequestDto
import com.entry.exception.DatabaseException
import com.entry.exception.InvalidRequestException
import com.entry.exception.ResourceAlreadyExistsException
import com.entry.model.user.User
import com.entry.repository.UserRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service

@Service
class UserService(
    @param:Qualifier("passwordPolicy")
    private val passwordPolicy: CharacterPolicy,
    @param:Qualifier("usernamePolicy")
    private val usernamePolicy: CharacterPolicy,
    private val userRepository: UserRepository,
) {
    fun create(createUserRequest: CreateUserRequestDto): MessageResponseDto {
        if (createUserRequest.username.isBlank() || createUserRequest.password.isBlank()) {
            throw InvalidRequestException("Username or password is missing")
        }

        if (passwordPolicy.matches(createUserRequest.password) || usernamePolicy.matches(createUserRequest.username)) {
            val policies = "$passwordPolicy\n$usernamePolicy"
            throw InvalidRequestException(
                "Username or password is invalid. These must comply with the following character policies:\n$policies",
            )
        }

        try {
            val exists = userRepository.existsByUsername(createUserRequest.username)
            if (exists) {
                throw ResourceAlreadyExistsException("User ${createUserRequest.username} already exists")
            }
        } catch (e: DataAccessException) {
            throw DatabaseException("Unable to check if username exists: ${e.message}")
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
            message = "user ${createUserRequest.username} created",
        )
    }
}
