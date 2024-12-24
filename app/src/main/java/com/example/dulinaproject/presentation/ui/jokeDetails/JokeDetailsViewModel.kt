package com.example.dulinaproject.presentation.ui.jokeDetails

import androidx.lifecycle.ViewModel
import com.example.dulinaproject.domain.entity.Joke
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.Serializable

class JokeDetailsViewModel : ViewModel() {

    private val _joke = MutableStateFlow(Joke())
    val joke: StateFlow<Joke> = _joke

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun loadJokeDetailsData(joke: Serializable?) {
        if (joke == null) {
            _error.value = ERROR_JOKE_POSITION
        } else {
            val jokeData = joke as Joke
            _joke.value = jokeData
        }
    }

    companion object {
        private const val ERROR_JOKE_POSITION = "Invalid joke position"
    }

}