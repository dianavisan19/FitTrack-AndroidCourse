package com.example.fitnessapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userNameTextView = view.findViewById(R.id.user_name)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDao = ApplicationController
            .instance?.appDatabase?.userDao()!!

        val userId = SharedPrefsManager.readToken()

        if (userId.toInt() != -1) {
            getUserById(userId)
        } else {
            // Handle error
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
                val fullName = it.fullName
                userNameTextView.text = "Hello, $fullName"
            } ?: run {
                userNameTextView.text = "User Not Found"
            }
        }
    }

}