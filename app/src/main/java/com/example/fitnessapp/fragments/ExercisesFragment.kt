package com.example.fitnessapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.ExerciseModel
import com.example.fitnessapp.helpers.ExerciseListAdapter
import org.json.JSONArray

/**
 * A simple [Fragment] subclass.
 * Use the [ExercisesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */class ExercisesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciseListAdapter: ExerciseListAdapter
    private lateinit var categoryNameTextView: TextView
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercises, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        categoryNameTextView = view.findViewById(R.id.categoryNameTextView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        exerciseListAdapter = ExerciseListAdapter(requireContext())
        recyclerView.adapter = exerciseListAdapter

        requestQueue = Volley.newRequestQueue(context)

        val category = arguments?.getString("category_name") ?: ""
        if (category.isEmpty()) {
            loadAllExercises()
        } else {
            categoryNameTextView.text = category
            loadExercisesByCategory(category)
        }

        return view
    }

    private fun loadExercisesByCategory(category: String) {
        val url = "http://10.0.2.2:5000/exercises/category/$category"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val exercises = parseExercises(response)
                exerciseListAdapter.setExercises(exercises)
            },
            { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun loadAllExercises() {
        val url = "http://10.0.2.2:5000/exercises"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val exercises = parseExercises(response)
                exerciseListAdapter.setExercises(exercises)
            },
            { error ->
                Log.e("ExercisesFragment", "Error: ${error.message}", error)
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun parseExercises(response: JSONArray): List<ExerciseModel> {
        val exerciseModels = mutableListOf<ExerciseModel>()
        for (i in 0 until response.length()) {
            val exerciseJson = response.getJSONObject(i)
            val exerciseModel = ExerciseModel(
                id = exerciseJson.getInt("id"),
                categoryName = exerciseJson.getString("categoryName"),
                name = exerciseJson.getString("name"),
                reps = exerciseJson.getInt("reps"),
                sets = exerciseJson.getInt("sets")
            )
            exerciseModels.add(exerciseModel)
        }
        return exerciseModels
    }
}