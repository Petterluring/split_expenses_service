package com.entry.controller

import com.entry.dto.StatusDto
import com.entry.service.StatusService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatusController(
    private val statusService: StatusService
) {

    @GetMapping("/status")
    fun status(): StatusDto {
        return StatusDto(
            status = "Boot Spring server is UP"
        )
    }

    @GetMapping("db_status")
    fun dbStatus(): StatusDto {
        return statusService.getDbStatus()
    }
}