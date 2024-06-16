package com.example.fitnessapp


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fitnessapp.dao.Category
import com.example.fitnessapp.dao.CategoryDao
import com.example.fitnessapp.dao.ExerciseDao
import com.example.fitnessapp.dao.ExerciseModel
import com.example.fitnessapp.dao.UserModel
import com.example.fitnessapp.dao.UserDao


@Database(entities = [UserModel::class, Category::class, ExerciseModel::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao

    abstract fun exerciseDao(): ExerciseDao


    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "fitness_db"
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }
}