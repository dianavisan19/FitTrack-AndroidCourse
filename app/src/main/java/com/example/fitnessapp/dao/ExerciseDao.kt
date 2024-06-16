package com.example.fitnessapp.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises WHERE id = :id")
    fun getExerciseById(id: Int): ExerciseModel
}