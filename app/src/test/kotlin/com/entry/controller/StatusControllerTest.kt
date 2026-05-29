package com.entry.controller

import com.entry.dto.StatusDto
import com.entry.service.StatusService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class StatusControllerTest {
    private lateinit var client: RestTestClient

    @Mock
    private lateinit var statusService: StatusService

    @BeforeEach
    fun setUp() {
        val controller = StatusController(statusService)
        client = RestTestClient.bindToController(controller).build()
    }

    @Test
    fun `can get status`() {
        val result =
            client
                .get()
                .uri("/status/server")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody<StatusDto>()
                .returnResult()
                .responseBody

        assertEquals("Boot Spring server is UP", result?.status)
    }

    @Test
    fun `can get db status`() {
        given(statusService.getDbStatus())
            .willReturn(
                StatusDto(
                    status = "MongoDB server is UP",
                ),
            )

        val result =
            client
                .get()
                .uri("/status/database")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody<StatusDto>()
                .returnResult()
                .responseBody

        assertEquals("MongoDB server is UP", result?.status)
    }
}
