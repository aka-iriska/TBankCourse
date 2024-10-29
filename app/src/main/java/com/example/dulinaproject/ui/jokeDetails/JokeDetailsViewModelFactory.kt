package com.example.dulinaproject.ui.jokeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JokeDetailsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokeDetailsViewModel::class.java) -> {
                JokeDetailsViewModel() as T
            }

            else -> throw IllegalArgumentException("Invalid ViewModel class")
        }
    }

}