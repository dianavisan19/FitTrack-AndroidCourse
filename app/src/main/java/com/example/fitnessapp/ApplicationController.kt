package com.example.fitnessapp

import android.app.Application

class ApplicationController : Application() {

//    lateinit var appDatabase: AppDatabase

    //    companion object {
//        var instance: ApplicationController? = null
//            private set
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//
//        instance = this
//
//        appDatabase = Room.databaseBuilder(
//            this,
//            AppDatabase::class.java,
//            "fitness-app"
//        )
//            .fallbackToDestructiveMigration() // DEVELOPMENT ONLY
//            .build()
//    }
    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.getDatabase(this)!!
    }

}