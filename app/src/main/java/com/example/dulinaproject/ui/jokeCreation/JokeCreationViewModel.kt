package com.example.dulinaproject.ui.jokeCreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.JokeData

class JokeCreationViewModel : ViewModel() {

    private val _savedSuccessfully = MutableLiveData<Boolean>()
    val savedSuccessfully: LiveData<Boolean> = _savedSuccessfully

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun saveJoke(joke: Joke) {
        if (joke.category.isNotBlank() && joke.question.isNotBlank() && joke.answer.isNotBlank()) {
            JokeData.addJoke(joke) // Сохраняем шутку в репозиторий
            _savedSuccessfully.value = true
            return
        }
        _error.value = "Все поля должны быть заполнены"
    }
}