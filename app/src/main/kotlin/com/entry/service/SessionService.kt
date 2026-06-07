package com.entry.service

import com.entry.idgenerator.IdGenerator
import com.entry.repository.SessionRepository
import com.entry.repository.UserRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class SessionService(
    @param:Qualifier("sessionIdGenerator")
    private val sessionIdGenerator: IdGenerator<String>,
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
) {
    fun createSession() {
    }
}
