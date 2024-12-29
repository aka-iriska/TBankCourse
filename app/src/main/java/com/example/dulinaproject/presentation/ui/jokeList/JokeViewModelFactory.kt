package com.example.dulinaproject.presentation.ui.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dulinaproject.domain.usecase.ClearOldCacheUseCase
import com.example.dulinaproject.domain.usecase.GetApiJokesUseCase
import com.example.dulinaproject.domain.usecase.GetCachedJokesUseCase
import com.example.dulinaproject.domain.usecase.GetUserJokesUseCase
import com.example.dulinaproject.domain.usecase.SaveUserJokesUseCase

class JokeListViewModelFactory(
    private val getApiJokes: GetApiJokesUseCase,
    private val getUserJokesUseCase: GetUserJokesUseCase,
    private val getCachedJokes: GetCachedJokesUseCase,
    private val saveUserJokes: SaveUserJokesUseCase,
    private val clearOldCache: ClearOldCacheUseCase
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
