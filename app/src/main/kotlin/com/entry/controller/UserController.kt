package com.entry.controller

import com.entry.constants.Status
import com.entry.dto.error.ErrorResponseDto
import com.entry.dto.user.CreateUserRequestDto
import com.entry.dto.user.UserResponseDto
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
    private val userService: UserService
) {

    @PostMapping("/create")
    fun createUser(
        @RequestBody createUserRequestDto: CreateUserRequestDto
    ): ResponseEntity<Any> {

        val username = createUserRequestDto.username
        when (userService.create(createUserRequestDto)) {
            Status.CREATED -> {
                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                        UserResponseDto(
                            username,
                            Status.CREATED.name.lowercase()
                        )
                    )
            }

            Status.EXISTS -> {
                return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                        ErrorResponseDto(
                            "User or password already exists"
                        )
                    )
            }

            Status.INVALID -> {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                        ErrorResponseDto(
                            "Invalid username or password"
                        )
                    )
            }

            else -> {
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                        ErrorResponseDto(
                            "Internal Server Error"
                        )
                    )
            }
        }
    }
}
