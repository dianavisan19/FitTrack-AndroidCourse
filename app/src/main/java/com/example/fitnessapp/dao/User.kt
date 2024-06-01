package com.example.fitnessapp.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val username: String,
    val password: String
)
data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val user: User?
)

data class RegisterResponse(
    val success: Boolean,
    val message: String?
)
