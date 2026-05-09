package com.entry.controller

import com.entry.dto.HealthDto
import com.entry.service.HealthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController(
    private val healthService: HealthService
) {

    @GetMapping("/status")
    fun status(): HealthDto {
        return healthService.getHealth()
    }
}