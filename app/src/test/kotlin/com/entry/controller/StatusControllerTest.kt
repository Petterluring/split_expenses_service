package com.entry.controller

import com.entry.dto.StatusDto
import com.entry.service.StatusService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.mockito.BDDMockito.given
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [StatusController::class])
class StatusControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockitoBean
    lateinit var statusService: StatusService

    @Test
    fun `can get status`() {
        mvc.perform(get("/status/server"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("Boot Spring server is UP"))
    }

    @Test
    fun `can get db status`() {
        given(statusService.getDbStatus())
            .willReturn(StatusDto(
                status = "MongoDB server is UP",
            ))

        mvc.perform(get("/status/database"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("MongoDB server is UP"))

    }

}