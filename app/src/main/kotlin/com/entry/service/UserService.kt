package com.entry.service

import com.entry.constants.Status
import com.entry.dto.user.CreateUserRequestDto
import org.springframework.stereotype.Service


@Service
class UserService {

    fun create(user: CreateUserRequestDto): Status {
        return Status.CREATED
    }
}