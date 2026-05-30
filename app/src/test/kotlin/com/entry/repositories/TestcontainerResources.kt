package com.entry.repositories

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.mongodb.MongoDBContainer
import org.testcontainers.utility.DockerImageName

@Testcontainers
interface TestcontainerResources {
    companion object {
        val mongoDbContainer: MongoDBContainer =
            MongoDBContainer(DockerImageName.parse("mongo:7.0"))
                .withReuse(true)

        @JvmStatic
        @DynamicPropertySource
        fun mongoProperties(registry: DynamicPropertyRegistry) {
            println("DynamicPropertySource was called")
            registry.add("spring.mongodb.host") {
                mongoDbContainer.host
            }
            registry.add("spring.mongodb.database") {
                "test"
            }
            registry.add("spring.mongodb.port") {
                mongoDbContainer.getMappedPort(27017)
            }
        }

        init {
            mongoDbContainer.start()
            println("Mongo URI = ${mongoDbContainer.replicaSetUrl}")
            println("Mongo Port = ${mongoDbContainer.getMappedPort(27017)}")
        }
    }
}
