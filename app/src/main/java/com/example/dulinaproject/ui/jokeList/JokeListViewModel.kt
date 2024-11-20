package com.example.dulinaproject.ui.jokeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.JokeData
import kotlinx.coroutines.delay

class JokeListViewModel : ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getJokesList() {
        try {
            _isLoading.value = true
            // Имитация задержки
            delay(10000)
            _jokes.value = JokeData.getJokes()
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Ошибка загрузки данных"
        }
    }
}