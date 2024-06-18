package com.example.fitnessapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.fitnessapp.R
import com.example.fitnessapp.helpers.SharedPrefsManager


class DisplayFragment : Fragment() {
    private lateinit var textViewSavedItem: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)
        textViewSavedItem = view.findViewById<TextView>(R.id.textView_savedItem)

        // Retrieve the list from SharedPreferences
        val savedList = SharedPrefsManager.readList("myListKey")

        // Convert the list to a string to display in the TextView
        val savedItemsString = savedList.joinToString(separator = "\n")

        // Display the items in the TextView
        textViewSavedItem.text = savedItemsString

        return view
    }
}