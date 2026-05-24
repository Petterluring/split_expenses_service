package com.entry.controller

import com.entry.dto.generic.ResponseDto
import com.entry.dto.user.CreateUserRequestDto
import com.entry.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/create")
    fun createUser(
        @RequestBody createUserRequestDto: CreateUserRequestDto,
    ): ResponseEntity<ResponseDto> {
        val response = userService.create(createUserRequestDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response)
    }
}
