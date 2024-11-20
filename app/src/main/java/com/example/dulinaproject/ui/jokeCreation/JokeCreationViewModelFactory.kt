package com.example.dulinaproject.ui.jokeCreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JokeCreationViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokeCreationViewModel::class.java) -> {
                JokeCreationViewModel() as T
            }

            else -> throw IllegalArgumentException("Invalid ViewModel class")
        }
    }

}