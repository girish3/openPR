package com.girish.openpr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.girish.openpr.model.repository.ProjectRepository

class ViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PRViewModel::class.java)) {
            return PRViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}