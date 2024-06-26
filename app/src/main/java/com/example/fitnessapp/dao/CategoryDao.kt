package com.example.fitnessapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: Category)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM categories")
    fun getAllCategoriesLiveData(): LiveData<List<Category>>

}

