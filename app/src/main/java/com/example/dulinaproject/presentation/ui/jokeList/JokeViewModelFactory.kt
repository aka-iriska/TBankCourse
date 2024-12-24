package com.example.dulinaproject.presentation.ui.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dulinaproject.domain.usecase.ClearOldCache
import com.example.dulinaproject.domain.usecase.GetApiJokes
import com.example.dulinaproject.domain.usecase.GetCachedJokes
import com.example.dulinaproject.domain.usecase.GetUserJokesUseCase
import com.example.dulinaproject.domain.usecase.SaveUserJokes

class JokeListViewModelFactory(
    private val getApiJokes: GetApiJokes,
    private val getUserJokesUseCase: GetUserJokesUseCase,
    private val getCachedJokes: GetCachedJokes,
    private val saveUserJokes: SaveUserJokes,
    private val clearOldCache: ClearOldCache
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JokeListViewModel(
                getApiJokes,
                getUserJokesUseCase,
                getCachedJokes,
                saveUserJokes,
                clearOldCache
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
