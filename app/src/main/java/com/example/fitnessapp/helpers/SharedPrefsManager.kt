package com.example.fitnessapp.helpers

import android.content.Context
import android.content.SharedPreferences
import com.example.fitnessapp.ApplicationController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPrefsManager {

    private const val ARG_TOKEN = "ARG_TOKEN"
    private const val SESSION_FILE_NAME = "cstacademyunibuc"

    private val gson = Gson()

    private val sharedPreferences: SharedPreferences? by lazy {
        ApplicationController.instance?.applicationContext?.getSharedPreferences(
            SESSION_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun writeToken(value: Long) {
        write(ARG_TOKEN, value)
    }

     fun write(key: String, value: Long) = this.sharedPreferences?.let { sharedPrefs ->
        with (sharedPrefs.edit()) {
            putLong(key, value)
            apply()
        }
    }

    fun write(key: String, value: String) = this.sharedPreferences?.let { sharedPrefs ->
        with (sharedPrefs.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun readToken(): Long = readLong(ARG_TOKEN)

    private fun readLong(key: String): Long = sharedPreferences?.getLong(key, 0L) ?: 0L
     fun read(key: String): String = sharedPreferences?.getString(key, "") ?: ""

    fun removeToken() = remove(ARG_TOKEN)

    private fun remove(key: String) = this.sharedPreferences?.let { sharedPrefs ->
        with (sharedPrefs.edit()) {
            remove(key)
            apply()
        }
    }
    // New methods for saving and retrieving a list
    fun writeList(key: String, list: List<String>) = this.sharedPreferences?.let { sharedPrefs ->
        val jsonString = gson.toJson(list)
        with(sharedPrefs.edit()) {
            putString(key, jsonString)
            apply()
        }
    }

    fun readList(key: String): List<String> = this.sharedPreferences?.let { sharedPrefs ->
        val jsonString = sharedPrefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        gson.fromJson(jsonString, type)
    } ?: emptyList()

    // New method to add an item to the list
    fun addToList(key: String, item: String) = this.sharedPreferences?.let { sharedPrefs ->
        val currentList = readList(key).toMutableList()
        currentList.add(item)
        writeList(key, currentList)
    }
}