package com.example.fitnessapp.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryName: String,
    val name: String,
    val reps: Int,
    val sets: Int
)
