package com.example.fitnessapp

import android.app.Application
import androidx.room.Room

class ApplicationController : Application() {

    lateinit var appDatabase: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
    }

    companion object {
        var instance: ApplicationController? = null
            private set
    }


}