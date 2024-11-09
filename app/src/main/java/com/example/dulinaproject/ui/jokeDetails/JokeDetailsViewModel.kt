package com.example.dulinaproject.ui.jokeDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.JokeData

class JokeDetailsViewModel : ViewModel() {

    private val _joke = MutableLiveData<Joke>()
    val joke: LiveData<Joke> = _joke

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadJokeDetailsData(jokePosition: Int) {
        if (jokePosition == -1) {
            _error.value = "Invalid joke position"
        } else {
            _joke.value = JokeData.data[jokePosition]
        }
    }

}