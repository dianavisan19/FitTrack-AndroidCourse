package com.example.fitnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.ExerciseModel
import com.example.fitnessapp.helpers.WorkoutAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class WorkoutFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val exercises = mutableListOf<ExerciseModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_workouts)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val workoutName = arguments?.getString("workoutName")
        val exerciseIds = arguments?.getIntegerArrayList("exerciseIds")
        exerciseIds?.let { fetchExercises(it) }
    }

    private fun fetchExercises(exerciseIds: ArrayList<Int>) {
        val client = OkHttpClient()
        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject().put("exerciseIds", JSONArray(exerciseIds)).toString()
        )
        val request = Request.Builder()
            .url("http://your.api.url/exercises")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val jsonArray = JSONArray(it.string())
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val exercise = ExerciseModel(
                            id = jsonObject.getInt("id"),
                            name = jsonObject.getString("name"),
                            categoryName = jsonObject.getString("categoryName"),
                            reps = jsonObject.getInt("reps"),
                            sets = jsonObject.getInt("sets")
                        )
                        exercises.add(exercise)
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                        val adapter = WorkoutAdapter(exercises)
                        recyclerView.adapter = adapter
                    }
                }
            }
        })
    }
}
