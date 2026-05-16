package com.entry.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "status")
data class Health(
    @Id
    val id: String? = null,
    val status: String
)
