package com.entry.repository

import com.entry.model.session.Session
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : MongoRepository<Session, String> {
    fun findBySessionId(sessionId: String): Session?
}
