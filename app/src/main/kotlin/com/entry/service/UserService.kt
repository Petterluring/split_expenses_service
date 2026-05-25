package com.entry.service

import com.entry.dto.generic.MessageResponseDto
import com.entry.dto.user.CreateUserRequestDto
import com.entry.exception.InvalidRequestException
import org.springframework.stereotype.Service

@Service
class UserService {
    fun create(user: CreateUserRequestDto): MessageResponseDto {
        if (user.username.isBlank() || user.password.isBlank()) {
            throw InvalidRequestException("Username or password is missing")
        }

        return MessageResponseDto(
            message = "user created",
        )
    }
}
