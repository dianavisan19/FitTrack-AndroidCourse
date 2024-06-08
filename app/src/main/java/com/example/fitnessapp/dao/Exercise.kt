package com.example.fitnessapp.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryName: String,
    val name: String
)
