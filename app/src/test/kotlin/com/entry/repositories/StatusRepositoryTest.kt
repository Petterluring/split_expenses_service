package com.entry.repositories

import com.entry.repository.StatusRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import kotlin.test.assertTrue

@SpringBootTest
class StatusRepositoryTest : TestcontainerResources {

    @Autowired
    lateinit var repository: StatusRepository

    @Test
    fun `can connect to mongo`() {
        assertTrue(
            repository.testMongoConnection(),
            "Failed to connect to mongo",
        )
    }
}
