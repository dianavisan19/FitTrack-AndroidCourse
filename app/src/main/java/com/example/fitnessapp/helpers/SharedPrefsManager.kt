package com.example.fitnessapp.helpers

import android.content.Context
import android.content.SharedPreferences
import com.example.fitnessapp.ApplicationController

object SharedPrefsManager {

    private const val ARG_TOKEN = "ARG_TOKEN"
    private const val SESSION_FILE_NAME = "cstacademyunibuc"

    private val sharedPreferences: SharedPreferences? by lazy {
        ApplicationController.instance?.applicationContext?.getSharedPreferences(
            SESSION_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun writeToken(value: Long) {
        write(ARG_TOKEN, value)
    }

    private fun write(key: String, value: Long) = this.sharedPreferences?.let { sharedPrefs ->
        with (sharedPrefs.edit()) {
            putLong(key, value)
            apply()
        }
    }

    fun readToken(): Long = readLong(ARG_TOKEN)

    private fun readLong(key: String): Long = sharedPreferences?.getLong(key, 0L) ?: 0L

    fun removeToken() = remove(ARG_TOKEN)

    private fun remove(key: String) = this.sharedPreferences?.let { sharedPrefs ->
        with (sharedPrefs.edit()) {
            remove(key)
            apply()
        }
    }
}