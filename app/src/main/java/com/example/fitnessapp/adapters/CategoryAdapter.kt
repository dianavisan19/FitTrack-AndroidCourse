package com.example.fitnessapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitnessapp.dao.Category
import com.example.fitnessapp.databinding.ItemCategoryBinding
import com.example.fitnessapp.R

class CategoryAdapter(private val onItemClick: (Category) -> Unit) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val onItemClick: (Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category

            val imageName = category.name.lowercase().replace(" ", "_")
            val context = binding.root.context
            val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

            Glide.with(context)
                .load(resId)
                .placeholder(R.drawable.default_icon)
                .error(R.drawable.default_icon)
                .override(48, 48)
                .into(binding.categoryIcon)

            binding.root.setOnClickListener {
                onItemClick(category)
            }

            binding.executePendingBindings()
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}
