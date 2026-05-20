package com.entry.service

import com.entry.repository.StatusRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class StatusServiceTest {

    @Mock
    lateinit var statusRepository: StatusRepository

    lateinit var statusService: StatusService

    @BeforeEach
    fun setUp() {
        statusService = StatusService(statusRepository)
    }


    @Test
    fun `can identify mongo status as up`() {
        given(statusRepository.testMongoConnection())
            .willReturn(true)

        val dto = statusService.getDbStatus()
        assertEquals(dto.status, "MongoDB is UP")
    }

    @Test
    fun `can identify mongo status as not responsive`() {
        given(statusRepository.testMongoConnection())
            .willReturn(false)

        val dto = statusService.getDbStatus()
        assertEquals(dto.status, "MongoDB is not responsive")

    }
}