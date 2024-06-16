package com.example.fitnessapp.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.ExerciseModel

class WorkoutAdapter(
    private val exercises: List<ExerciseModel>
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseNameTextView: TextView = itemView.findViewById(R.id.text_exercise_name)

        fun bind(exercise: ExerciseModel) {
            exerciseNameTextView.text = exercise.name
        }
    }
}