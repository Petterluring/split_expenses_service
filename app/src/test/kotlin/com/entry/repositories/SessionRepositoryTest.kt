package com.entry.repositories

import com.entry.model.session.Session
import com.entry.repository.SessionRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest
import kotlin.test.assertEquals
import kotlin.time.Instant

@DataMongoTest
class SessionRepositoryTest : TestcontainerResources {
    @Autowired
    lateinit var sessionRepository: SessionRepository

    @BeforeEach
    fun setUp() {
        sessionRepository.deleteAll()
    }

    @Test
    fun `can fetch session by sessionId`() {
        val session =
            Session(
                sessionId = "some session id",
                username = "username",
                endsAt = Instant.parse("2026-06-02T00:00:00Z"),
            )

        sessionRepository.save(session)

        val fetchedSession: Session? =
            sessionRepository.findBySessionId(
                "some session id",
            )

        assertNotNull(fetchedSession)
        assertEquals("some session id", fetchedSession.sessionId)
        assertEquals("username", fetchedSession.username)
        assertEquals("2026-06-02T00:00:00Z", fetchedSession.endsAt.toString())
    }
}
