package com.entry.repository

import org.springframework.stereotype.Repository


/**
 * Fake repository that simulates communication with a database
 * by simply returning a prefixed value. This repository is for demonstrative purposes.
 */
@Repository
class HealthRepository {

    fun findHealth(): String {
        return "UP"
    }
}