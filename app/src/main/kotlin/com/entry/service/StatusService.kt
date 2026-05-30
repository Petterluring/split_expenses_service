package com.entry.service

import com.entry.dto.StatusDto
import com.entry.repository.StatusRepository
import org.springframework.stereotype.Service

@Service
class StatusService(
    private val statusRepository: StatusRepository,
) {
    fun getDbStatus(): StatusDto {
        val isUp = statusRepository.testMongoConnection()
        return if (isUp) {
            StatusDto(
                status = "MongoDB is UP",
            )
        } else {
            StatusDto(
                status = "MongoDB is not responsive",
            )
        }
    }
}
