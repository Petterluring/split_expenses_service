package com.entry.controller

import com.entry.dto.generic.MessageResponseDto
import com.entry.dto.user.CreateUserRequestDto
import com.entry.dto.user.DeleteUserRequestDto
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
    ): ResponseEntity<MessageResponseDto> {
        val response = userService.create(createUserRequestDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response)
    }

    @PostMapping("/delete")
    fun deleteUser(
        @RequestBody deleteUserRequestDto: DeleteUserRequestDto,
    ): ResponseEntity<MessageResponseDto> {
        val response = userService.delete(deleteUserRequestDto)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }
}
