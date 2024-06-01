package com.example.fitnessapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fitnessapp.ApplicationController
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.UserDao
import com.example.fitnessapp.helpers.SharedPrefsManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var userDao: UserDao
    private lateinit var userNameTextView: TextView
    private lateinit var userActivityLevelSpinner: Spinner
    private lateinit var userAgeEditText: EditText
    private lateinit var userHeightEditText: EditText
    private lateinit var userWeightEditText: EditText
    private lateinit var btnSaveChanges: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userNameTextView = view.findViewById(R.id.tv_username)
        userActivityLevelSpinner = view.findViewById(R.id.spinner_activity_level)
        userAgeEditText = view.findViewById(R.id.et_age)
        userHeightEditText = view.findViewById(R.id.et_height)
        userWeightEditText = view.findViewById(R.id.et_weight)
        btnSaveChanges = view.findViewById(R.id.btn_save_changes)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDao = ApplicationController.instance?.appDatabase?.userDao()!!

        val userId = SharedPrefsManager.readToken()

        if (userId.toInt() != -1) {
            GlobalScope.launch(Dispatchers.Main) {
                val isProfileComplete = withContext(Dispatchers.IO) {
                    userDao.isProfileComplete(userId)
                }
                if (isProfileComplete) {
                    getUserById(userId)
                }
            }
        }
        btnSaveChanges.setOnClickListener {
            val age = userAgeEditText.text.toString().toIntOrNull() ?: 0
            val height = userHeightEditText.text.toString().toDoubleOrNull() ?: 0.0
            val weight = userWeightEditText.text.toString().toDoubleOrNull() ?: 0.0
            val activityLevel = userActivityLevelSpinner.selectedItem.toString()

            updateUserProfile(userId, age, height, weight, activityLevel)
        }
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun getUserById(userId: Long) {
        GlobalScope.launch(Dispatchers.Main) {
            val user = withContext(Dispatchers.IO) {
                userDao.getUserById(userId)
            }
            user?.let {
                userNameTextView.text = "Hello, ${it.fullName}"
                val activityLevels = resources.getStringArray(R.array.activity_levels)
                val activityLevelIndex = activityLevels.indexOf(it.activityLevel)
                userActivityLevelSpinner.setSelection(activityLevelIndex)
                userAgeEditText.setText(it.age.toString())
                userHeightEditText.setText(it.height.toString())
                userWeightEditText.setText(it.weight.toString())
            } ?: run {
                userNameTextView.text = "User Not Found"
            }
        }
    }

    private fun updateUserProfile(
        userId: Long,
        age: Int,
        height: Double,
        weight: Double,
        activityLevel: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val user = userDao.getUserById(userId)
            user?.let {
                val updatedUser = it.copy(
                    age = age,
                    height = height,
                    weight = weight,
                    activityLevel = activityLevel
                )
                userDao.update(updatedUser)
            }
        }
    }
}