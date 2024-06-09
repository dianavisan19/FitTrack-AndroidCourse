package com.example.fitnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.WorkoutModel
import com.example.fitnessapp.helpers.VolleyRequestQueue
import com.example.fitnessapp.helpers.WorkoutListAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken

class CategoryDetailsFragment : Fragment() {

    private lateinit var categoryName: String
    private val workouts = ArrayList<WorkoutModel>()
    private val adapter = WorkoutListAdapter(workouts)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_category_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryName = arguments?.getString("category_name") ?: ""

        view.findViewById<TextView>(R.id.category_title).text = categoryName

        setupRecyclerView(view)
        fetchWorkouts()
    }

    private fun setupRecyclerView(view: View) {
        val layoutManager = LinearLayoutManager(context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_workouts)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun fetchWorkouts() {
        val url = "http://10.0.2.2:5000/exercises/category/$categoryName"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                handleWorkoutsResponse(response)
            },
            {
                it.printStackTrace()
            }
        )

        VolleyRequestQueue.addToRequestQueue(stringRequest)
    }

    private fun handleWorkoutsResponse(response: String) {
        println("JSON Response: $response")
        val gson = Gson()
        val jsonArray = gson.fromJson(response, JsonArray::class.java)

        workouts.clear()

        for (jsonElement in jsonArray) {
            val workout = gson.fromJson(jsonElement, WorkoutModel::class.java)
            workouts.add(workout)
        }

        adapter.notifyDataSetChanged()

        workouts.forEach { workout ->
            println("Workout: ${workout.workoutName}, Exercises: ${workout.exercises}")
        }
    }
}