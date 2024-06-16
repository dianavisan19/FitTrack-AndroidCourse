package com.example.fitnessapp.helpers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.WorkoutModel

class WorkoutListAdapter(private val workouts: List<WorkoutModel>) :
    RecyclerView.Adapter<WorkoutListAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workoutNameTextView: TextView = itemView.findViewById(R.id.workout_name)
        val startButton: Button = itemView.findViewById(R.id.start_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentItem = workouts[position]
        holder.workoutNameTextView.text = currentItem.workoutName
        holder.startButton.setOnClickListener {
            val bundle = Bundle().apply {
                putString("workoutName", currentItem.workoutName)
                putIntegerArrayList("exerciseIds", ArrayList(currentItem.exercises))
            }
            it.findNavController().navigate(R.id.action_categoryDetailsFragment_to_workoutFragment, bundle)
        }
    }

    override fun getItemCount() = workouts.size
}