package com.entry.service

import com.entry.characterpolicy.CharacterPolicy
import com.entry.dto.generic.MessageResponseDto
import com.entry.dto.user.CreateUserRequestDto
import com.entry.exception.InvalidRequestException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class UserService(
    @param:Qualifier("passwordPolicy")
    private val passwordPolicy: CharacterPolicy,
    @param:Qualifier("usernamePolicy")
    private val usernamePolicy: CharacterPolicy,
) {
    fun create(user: CreateUserRequestDto): MessageResponseDto {
        if (user.username.isBlank() || user.password.isBlank()) {
            throw InvalidRequestException("Username or password is missing")
        }

        if (passwordPolicy.matches(user.password) || usernamePolicy.matches(user.username)) {
            val policies = "${passwordPolicy.toString()}\n${usernamePolicy.toString()}"
            throw InvalidRequestException(
                "Username or password is invalid. These must comply with the following policies:\n${policies}",
            )
        }

        return MessageResponseDto(
            message = "user created",
        )
    }
}
