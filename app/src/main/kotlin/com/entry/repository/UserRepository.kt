package com.entry.repository

import com.entry.model.user.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun existsByUsername(username: String): Boolean

    fun deleteByUsernameAndHashedPassword(
        username: String,
        hashedPassword: String,
    ): Long
}
