package com.entry.repository

import com.entry.model.Health
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Fake repository that simulates communication with a database
 * by simply returning a prefixed value. This repository is for demonstrative purposes.
 */
interface HealthRepository : MongoRepository<Health, String> {

    fun findByStatus(status: String): Health?
}