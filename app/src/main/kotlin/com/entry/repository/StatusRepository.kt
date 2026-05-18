package com.entry.repository

import com.mongodb.MongoException
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository

/**
 * Fake repository that simulates communication with a database
 * by simply returning a prefixed value. This repository is for demonstrative purposes.
 */
@Repository
class StatusRepository(
    private val mongoTemplate: MongoTemplate,
) {

    fun testMongoConnection(): Boolean {
        return try {
            val result = mongoTemplate.executeCommand("{ ping: 1 }")
            result.get("ok") == 1.0
        } catch (_: MongoException) {
            false
        }
    }
}