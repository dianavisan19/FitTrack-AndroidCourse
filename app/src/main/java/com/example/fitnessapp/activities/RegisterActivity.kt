package com.example.fitnessapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessapp.ApplicationController
import com.example.fitnessapp.dao.User
import com.example.fitnessapp.dao.UserDao
import com.example.fitnessapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val TAG = "com.example.fitnessapp.activities.RegisterActivity"
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "com.example.fitnessapp.activities.RegisterActivity onCreate")

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            register(fullName, username, password)
        }

        binding.tvHasAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun register(fullName: String, username: String, password: String) {
        Log.d(TAG, "register function called")

        userDao = ApplicationController.appDatabase.userDao()!!;

        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Inside coroutine")

            val existingUser = userDao?.getUserByUsername(username)
            if (existingUser != null) {
                Log.d(TAG, "User already exists")
                // Show username already exists error
                return@launch
            }

            val newUser = User(
                fullName = fullName,
                username = username,
                password = password
            )

            userDao?.insert(newUser)
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }
}
