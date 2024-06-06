package com.example.fitnessapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.AppDatabase
import com.example.fitnessapp.dao.Category
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val categoryDao = AppDatabase.getInstance(application).categoryDao()
    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategoriesLiveData()

    fun insert(category: Category) = viewModelScope.launch {
        categoryDao.insert(category)
    }
}
