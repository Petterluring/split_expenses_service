package com.entry.controller

import com.entry.dto.generic.MessageResponseDto
import com.entry.dto.user.CreateUserRequestDto
import com.entry.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UserControllerTest {
    private lateinit var client: RestTestClient

    @Mock
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        val controller = UserController(userService)
        client = RestTestClient.bindToController(controller).build()
    }

    @Test
    fun `can create new user`() {
        given(
            userService.create(
                CreateUserRequestDto(
                    "cat",
                    "cat123",
                ),
            ),
        ).willReturn(
            MessageResponseDto(
                message = "user created",
            ),
        )

        val result =
            client
                .post()
                .uri("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    """
                    {
                      "username": "cat",
                      "password": "cat123"
                    }
                    """.trimIndent(),
                ).exchange()
                .expectStatus()
                .isCreated
                .expectBody<MessageResponseDto>()
                .returnResult()
                .responseBody

        assertEquals("user created", result?.message)
    }
}
