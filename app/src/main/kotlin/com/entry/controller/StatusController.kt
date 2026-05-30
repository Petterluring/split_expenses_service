package com.entry.controller

import com.entry.dto.StatusDto
import com.entry.service.StatusService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/status")
class StatusController(
    private val statusService: StatusService,
) {
    @GetMapping("/server")
    fun status(): StatusDto =
        StatusDto(
            status = "Boot Spring server is UP",
        )

    @GetMapping("/database")
    fun dbStatus(): StatusDto = statusService.getDbStatus()
}
