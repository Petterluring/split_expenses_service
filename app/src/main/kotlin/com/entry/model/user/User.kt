package com.entry.model.user

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Users")
data class User(
    val username: String,
    val hashedPassword: String,
)