package com.entry.controller.advice

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
}
