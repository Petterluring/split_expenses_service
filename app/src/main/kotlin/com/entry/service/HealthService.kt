package com.entry.service

import com.entry.dto.HealthDto
import com.entry.repository.HealthRepository
import org.springframework.stereotype.Service


@Service
class HealthService(
    private val healthRepository: HealthRepository
) {


    fun getHealth(): HealthDto {
        return HealthDto(
            status = healthRepository.findHealth()
        )
    }
}