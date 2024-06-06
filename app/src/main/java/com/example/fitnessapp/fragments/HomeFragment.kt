package com.example.fitnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.R
import com.example.fitnessapp.adapters.CategoryAdapter
import com.example.fitnessapp.databinding.FragmentHomeBinding
import com.example.fitnessapp.viewmodels.CategoryViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        val adapter = CategoryAdapter { category ->
            val bundle = Bundle().apply {
                putString("category_name", category.name)
            }
            findNavController().navigate(R.id.action_homeFragment_to_categoryDetailsFragment, bundle)
        }
        binding.recVWorkoutCategories.layoutManager = LinearLayoutManager(context)
        binding.recVWorkoutCategories.adapter = adapter

        categoryViewModel.allCategories.observe(viewLifecycleOwner, { categories ->
            categories?.let { adapter.submitList(it) }
        })

        val userIcon = view.findViewById<ImageView>(R.id.user_icon)
        userIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_userProfileFragment)
        }
    }
}
