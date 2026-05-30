package com.entry.repository

import com.mongodb.MongoException
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

/**
 * Repository for testing mongodb server connection
 */
@Repository
class StatusRepository(
    private val mongoTemplate: MongoTemplate,
) {
    fun testMongoConnection(): Boolean =
        try {
            val result = mongoTemplate.executeCommand("{ ping: 1 }")
            result["ok"] == 1.0
        } catch (_: MongoException) {
            false
        }
}
