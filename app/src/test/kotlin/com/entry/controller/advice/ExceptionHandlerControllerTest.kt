package com.entry.controller.advice

import com.entry.dto.generic.ErrorResponseDto
import com.entry.exception.DatabaseException
import com.entry.exception.InvalidRequestException
import com.entry.exception.ResourceAlreadyExistsException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.test.assertEquals

class ExceptionHandlerControllerTest {
    private lateinit var client: RestTestClient

    @BeforeEach
    fun setUp() {
        client = RestTestClient.bindToController(TestController(), ExceptionHandlerController()).build()
    }

    @Test
    fun `test handle invalid request exception`() {
        var result =
            client
                .get()
                .uri("/test/invalid-request-error")
                .exchange()
                .expectStatus()
                .isBadRequest
                .expectBody<ErrorResponseDto>()
                .returnResult()
                .responseBody

        assertEquals(400, result?.code)
        assertEquals("Bad Request", result?.error)
        assertEquals("Request is invalid", result?.message)

        result =
            client
                .get()
                .uri("/test/invalid-request-error-no-message")
                .exchange()
                .expectStatus()
                .isBadRequest
                .expectBody<ErrorResponseDto>()
                .returnResult()
                .responseBody

        assertEquals(400, result?.code)
        assertEquals("Bad Request", result?.error)
        assertEquals("Invalid request", result?.message)
    }

    @Test
    fun `test handle resource already exists exception`() {
        var result =
            client
                .get()
                .uri("/test/resource-exists-error")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT)
                .expectBody<ErrorResponseDto>()
                .returnResult()
                .responseBody

        assertEquals(409, result?.code)
        assertEquals("Conflict", result?.error)
        assertEquals("Resource already existsS", result?.message)

        result =
            client
                .get()
                .uri("/test/resource-exists-error-no-message")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT)
                .expectBody<ErrorResponseDto>()
                .returnResult()
                .responseBody

        assertEquals(409, result?.code)
        assertEquals("Conflict", result?.error)
        assertEquals("Resource already exists", result?.message)
    }

    @Test
    fun `test handle database exception`() {
        var result =
            client
                .get()
                .uri("/test/service-unavailable-error")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
                .expectBody<ErrorResponseDto>()
                .returnResult()
                .responseBody

        assertEquals(503, result?.code)
        assertEquals("Service Unavailable", result?.error)
        assertEquals("Database error", result?.message)

        result =
            client
                .get()
                .uri("/test/service-unavailable-error-no-message")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
                .expectBody<ErrorResponseDto>()
                .returnResult()
                .responseBody

        assertEquals(503, result?.code)
        assertEquals("Service Unavailable", result?.error)
        assertEquals("Database is temporarily unavailable", result?.message)
    }
}

@RestController
private class TestController {
    @GetMapping("/test/resource-exists-error")
    fun getResourceExistError(): Unit = throw ResourceAlreadyExistsException("Resource already existsS")

    @GetMapping("/test/resource-exists-error-no-message")
    fun getResourceExistErrorNoMessage(): Unit = throw ResourceAlreadyExistsException()

    @GetMapping("/test/invalid-request-error")
    fun getInvalidRequestError(): Unit = throw InvalidRequestException("Request is invalid")

    @GetMapping("/test/invalid-request-error-no-message")
    fun getInvalidRequestErrorNoMessage(): Unit = throw InvalidRequestException()

    @GetMapping("/test/service-unavailable-error")
    fun getServiceUnavailableError(): Unit = throw DatabaseException("Database error")

    @GetMapping("/test/service-unavailable-error-no-message")
    fun getServiceUnavailableErrorNoMessage(): Unit = throw DatabaseException()
}
