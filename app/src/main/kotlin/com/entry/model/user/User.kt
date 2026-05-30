package com.entry.model.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Users")
data class User(
    @Id
    val id: String? = null,
    val username: String,
    val hashedPassword: String,
)
