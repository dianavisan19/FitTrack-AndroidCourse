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
    ): View = inflater.inflate(R.layout.fragment_display, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewSavedItem = view.findViewById<TextView>(R.id.textView_savedItem)
        displaySavedItem()
    }

    private fun displaySavedItem() {
        val savedItem = SharedPrefsManager.read("savedItem")
        textViewSavedItem.text = savedItem}
}
