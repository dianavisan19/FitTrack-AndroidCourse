package com.example.fitnessapp.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.fitnessapp.R
import com.example.fitnessapp.dao.ExerciseModel
import pl.droidsonroids.gif.GifImageView

class ExerciseListAdapter(private val context: Context) : RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>() {

    private var exercises = listOf<ExerciseModel>()

    fun setExercises(exercises: List<ExerciseModel>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.exerciseNameTextView)
        private val repsTextView: TextView = itemView.findViewById(R.id.repsTv)
        private val setsTextView: TextView = itemView.findViewById(R.id.setsTv)
        private val gifImageView: GifImageView = itemView.findViewById(R.id.gifImageView)

        fun bind(exercise: ExerciseModel) {
            nameTextView.text = exercise.name.replace("_", " ")
            repsTextView.text = "Reps: ${exercise.reps}"
            setsTextView.text = "Sets: ${exercise.sets}"

            val gifName = exercise.name.toLowerCase().replace("_", "")
            val resourceId = context.resources.getIdentifier(gifName, "drawable", context.packageName)

            if (resourceId != 0) {
                Glide.with(context)
                    .asGif()
                    .load(resourceId)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                    .into(gifImageView)
            } else {
                Glide.with(context)
                    .asGif()
                    .load(R.drawable.default_gif)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                    .into(gifImageView)
            }
        }
    }
}