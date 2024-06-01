package com.example.fitnessapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessapp.ApplicationController
import com.example.fitnessapp.dao.UserDao
import com.example.fitnessapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = applicationContext as ApplicationController
        userDao = application.appDatabase.userDao()

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            login(username, password)
        }

        binding.tvNoAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login(username: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val user = userDao.getUserByUsernameAndPassword(username, password)
            if (user != null) {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            } else {
                // Handle login failure
            }
        }
    }
}