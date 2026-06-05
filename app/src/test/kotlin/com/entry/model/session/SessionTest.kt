package com.entry.model.session

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Instant

class SessionTest {
    @ParameterizedTest
    @CsvSource(
        "2026-06-05T00:00:00Z, false",
        "2026-06-06T00:00:00Z, false",
        "2026-06-07T00:00:00Z, false",
        "2026-06-08T00:00:00Z, false",
        "2026-06-09T00:00:00Z, false",
        "2026-06-09T12:00:00Z, false",
        "2026-06-11T00:00:00Z, true",
        "2026-06-12T00:00:00Z, true",
        "2026-06-13T00:00:00Z, true",
        "2026-06-14T00:00:00Z, true",
    )
    fun `can validate if instant is expired`(
        instant: String,
        expired: Boolean,
    ) {
        val exampleInstant = Instant.parse("2026-06-10T00:00:00Z")
        val session =
            Session(
                sessionId = "id_1",
                username = "username_1",
                endsAt = exampleInstant,
            )

        val testInstant = Instant.parse(instant)
        if (expired) {
            assertTrue(session.expired(testInstant))
        } else {
            assertFalse(session.expired(testInstant))
        }
    }
}
