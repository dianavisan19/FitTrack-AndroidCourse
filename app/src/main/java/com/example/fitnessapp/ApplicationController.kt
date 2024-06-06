package com.example.fitnessapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.fitnessapp.dao.UserDao

class ApplicationController: Application()  {
    lateinit var appDatabase: AppDatabase

    companion object {
        var instance: ApplicationController? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        appDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "fitness_db"
        )
            .fallbackToDestructiveMigration() // DEVELOPMENT ONLY
            .build()
    }

}