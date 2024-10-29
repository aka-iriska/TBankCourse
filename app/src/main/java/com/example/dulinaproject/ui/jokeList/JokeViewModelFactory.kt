package com.example.dulinaproject.ui.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JokeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokeListViewModel::class.java) -> {
                JokeListViewModel() as T
            }

            else -> throw IllegalArgumentException("Invalid ViewModel class")
        }
    }
}