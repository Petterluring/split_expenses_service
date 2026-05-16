package com.entry.service

import com.entry.dto.HealthDto
import com.entry.repository.HealthRepository
import org.springframework.stereotype.Service


@Service
class HealthService(
    private val healthRepository: HealthRepository
) {


    fun getHealth(): HealthDto {
        val health = healthRepository.findByStatus("UP")
        return if (health != null) {
            HealthDto(
                status = health.status,
            )
        } else {
            HealthDto(
                status = "DOWN"
            )
        }
    }
}