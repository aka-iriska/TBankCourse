package com.example.dulinaproject.ui.jokeList

import androidx.lifecycle.ViewModel
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.JokeData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JokeListViewModel : ViewModel() {

    private val _jokes = MutableStateFlow(emptyList<Joke>())
    val jokes: StateFlow<List<Joke>> = _jokes

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun getJokesList() {
        try {
            _isLoading.value = true
            // Имитация задержки
            delay(10000)
            _jokes.value = JokeData.getJokes()
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = ERROR_LOADING_MESSAGE
        }
    }

    companion object {
        private const val ERROR_LOADING_MESSAGE = "Ошибка загрузки данных"
    }
}