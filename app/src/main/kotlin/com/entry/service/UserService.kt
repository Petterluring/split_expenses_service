package com.entry.service

import com.entry.dto.generic.ResponseDto
import com.entry.dto.user.CreateUserRequestDto
import org.springframework.stereotype.Service

@Service
class UserService {
    fun create(user: CreateUserRequestDto): ResponseDto =
        ResponseDto(
            message = "user created",
        )
}
