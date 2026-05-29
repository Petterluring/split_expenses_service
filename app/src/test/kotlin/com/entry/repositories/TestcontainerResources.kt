package com.entry.repositories

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.mongodb.MongoDBContainer
import org.testcontainers.utility.DockerImageName

abstract class TestcontainerResources {
    companion object {
        val mongoDbContainer: MongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:7.0"))
            .withReuse(true)


        @JvmStatic
        @DynamicPropertySource
        fun mongoProperties(registry: DynamicPropertyRegistry) {
            println("DynamicPropertySource was called")
            registry.add("spring.data.mongodb.uri") {
                mongoDbContainer.replicaSetUrl
            }
        }

        init {
            mongoDbContainer.start()
            println("Mongo URI = ${mongoDbContainer.replicaSetUrl}")
        }
    }
}
