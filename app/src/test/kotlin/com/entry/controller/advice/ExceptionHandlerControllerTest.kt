package com.entry.controller.advice

import com.entry.exception.DatabaseException
import com.entry.exception.InvalidRequestException
import com.entry.exception.ResourceAlreadyExistsException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@WebMvcTest(TestController::class)
@Import(ExceptionHandlerController::class)
class ExceptionHandlerControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `test handle invalid request exception`() {
        mockMvc
            .get("/test/invalid-request-error")
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.code") { value(400) }
                jsonPath("$.error") { value("Bad Request") }
                jsonPath("$.message") { value("Request is invalid") }
            }

        mockMvc
            .get("/test/invalid-request-error-no-message")
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.code") { value(400) }
                jsonPath("$.error") { value("Bad Request") }
                jsonPath("$.message") { value("Invalid request") }
            }
    }

    @Test
    fun `test handle resource already exists exception`() {
        mockMvc
            .get("/test/resource-exists-error")
            .andExpect {
                status { isConflict() }
                jsonPath("$.code") { value(409) }
                jsonPath("$.error") { value("Conflict") }
                jsonPath("$.message") { value("Resource already existsS") }
            }

        mockMvc
            .get("/test/resource-exists-error-no-message")
            .andExpect {
                status { isConflict() }
                jsonPath("$.code") { value(409) }
                jsonPath("$.error") { value("Conflict") }
                jsonPath("$.message") { value("Resource already exists") }
            }
    }

    @Test
    fun `test handle database exception`() {
        mockMvc
            .get("/test/service-unavailable-error")
            .andExpect {
                status { isServiceUnavailable() }
                jsonPath("$.code") { value(503) }
                jsonPath("$.error") { value("Service Unavailable") }
                jsonPath("$.message") { value("Database error") }
            }

        mockMvc
            .get("/test/service-unavailable-error-no-message")
            .andExpect {
                status { isServiceUnavailable() }
                jsonPath("$.code") { value(503) }
                jsonPath("$.error") { value("Service Unavailable") }
                jsonPath("$.message") { value("Database is temporarily unavailable") }
            }
    }
}

@RestController
class TestController {
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
