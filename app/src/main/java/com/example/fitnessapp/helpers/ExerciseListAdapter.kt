package com.example.fitnessapp.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.ExerciseModel

class ExerciseListAdapter : RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>() {

    private var exerciseModels = listOf<ExerciseModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseModels[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int {
        return exerciseModels.size
    }

    fun setExercises(exerciseModels: List<ExerciseModel>) {
        this.exerciseModels = exerciseModels
        notifyDataSetChanged()
        Log.d("ExerciseListAdapter", "Exercises set: ${exerciseModels.size}")
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseNameTextView: TextView =
            itemView.findViewById(R.id.exerciseNameTextView)
        private val exerciseCategoryTextView: TextView =
            itemView.findViewById(R.id.exerciseCategoryTextView)

        fun bind(exerciseModel: ExerciseModel) {
            exerciseNameTextView.text = exerciseModel.name
            exerciseCategoryTextView.text = exerciseModel.categoryName
        }
    }
}