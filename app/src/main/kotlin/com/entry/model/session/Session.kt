package com.entry.model.session

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import kotlin.time.Instant

@Document(collection = "session")
class Session(
    @Id
    val id: String? = null,
    val sessionId: String,
    val username: String,
    val endsAt: Instant,
) {
    fun expired(instant: Instant): Boolean = endsAt < instant
}
