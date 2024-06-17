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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.ExerciseModel
import com.example.fitnessapp.helpers.ExerciseListAdapter
import org.json.JSONObject

class WorkoutDetailsFragment : Fragment() {

    private lateinit var workoutName: String
    private val exercises = ArrayList<ExerciseModel>()
    private lateinit var adapter: ExerciseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workoutName = arguments?.getString("workoutName") ?: ""

        view.findViewById<TextView>(R.id.workout_title).text = workoutName

        adapter = ExerciseListAdapter(requireContext())

        setupRecyclerView(view)
        fetchExercises()
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_exercises)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun fetchExercises() {
        val category = workoutName.split("_")[0].toLowerCase()

        val url = "http://10.0.2.2:5000/exercises/$category/$workoutName"

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                handleExercisesResponse(response)
            },
            { error ->
                error.printStackTrace()
            }
        )

        Volley.newRequestQueue(requireContext()).add(stringRequest)
    }

    private fun handleExercisesResponse(response: JSONObject) {
        println("JSON Response: $response")

        if (response.has("exercises")) {
            val exercisesArray = response.getJSONArray("exercises")
            val exercisesList = ArrayList<ExerciseModel>()

            for (i in 0 until exercisesArray.length()) {
                val exerciseObj = exercisesArray.getJSONObject(i)
                val id = exerciseObj.getInt("id")
                val categoryName = exerciseObj.getString("categoryName")
                val name = exerciseObj.getString("name")
                val reps = exerciseObj.getInt("reps")
                val sets = exerciseObj.getInt("sets")

                val exercise = ExerciseModel(id, categoryName, name, reps, sets)
                exercisesList.add(exercise)
            }

            exercises.clear()
            exercises.addAll(exercisesList)
            adapter.setExercises(exercisesList)
        } else {
            println("No 'exercises' array found in response.")
        }
    }
}