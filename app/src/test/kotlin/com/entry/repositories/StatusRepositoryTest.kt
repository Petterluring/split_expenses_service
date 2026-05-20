package com.entry.repositories

import com.entry.repository.StatusRepository
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.assertTrue


@Testcontainers
@DataMongoTest
class StatusRepositoryTest {

    companion object {

        @Container
        val mongo = MongoDBContainer("mongodb/mongodb-community-server:latest")

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") {
                mongo.replicaSetUrl
            }
        }
    }

    @Autowired
    lateinit var repository: StatusRepository

    @Test
    fun `can connect to mongo`() {
        assertTrue(
            repository.testMongoConnection(),
            "Failed to connect to mongo"
            )

    }

}