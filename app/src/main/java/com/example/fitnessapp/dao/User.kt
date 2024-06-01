package com.example.fitnessapp.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fullName: String,
    val username: String,
    val password: String,
    val activityLevel: String = "",
    val age: Int = 0,
    val height: Double = 0.0,
    val weight: Double = 0.0
)