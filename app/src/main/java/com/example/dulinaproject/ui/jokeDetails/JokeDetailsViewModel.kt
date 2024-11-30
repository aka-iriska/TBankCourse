package com.example.dulinaproject.ui.jokeDetails

import androidx.lifecycle.ViewModel
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.JokeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JokeDetailsViewModel : ViewModel() {

    private val _joke = MutableStateFlow(Joke())
    val joke: StateFlow<Joke> = _joke

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun loadJokeDetailsData(jokePosition: Int) {
        if (jokePosition == DEFAULT_JOKE_POSITION) {
            _error.value = ERROR_JOKE_POSITION
        } else {
            _joke.value = JokeData.getJokeByPosition(jokePosition)
        }
    }

    companion object {
        private const val DEFAULT_JOKE_POSITION = -1
        private const val ERROR_JOKE_POSITION = "Invalid joke position"
    }

}