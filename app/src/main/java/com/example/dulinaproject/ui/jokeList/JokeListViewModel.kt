package com.example.dulinaproject.ui.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulinaproject.data.Joke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JokeListViewModel : ViewModel() {

    private val _jokeList = mutableListOf<Joke>()

    private val _jokes =
        MutableStateFlow((listOf<Joke>())) // дабы обновлялся State при getJokesList
    val jokes: StateFlow<List<Joke>> = _jokes

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _savedSuccessfully = MutableStateFlow(false)
    val savedSuccessfully: StateFlow<Boolean> = _savedSuccessfully

    private var isDataChanged = true  // Флаг для проверки, были ли уже загружены данные

    fun loadJokes() {
        if (isDataChanged) {
            viewModelScope.launch {
                getJokesList()
            }
        }
    }

    private suspend fun getJokesList() {
        try {
            _isLoading.value = true
            // Имитация задержки
            delay(3000)
            _jokes.value = _jokeList
            _isLoading.value = false
            isDataChanged = false
        } catch (e: Exception) {
            _isLoading.value = false
            _error.value = ERROR_LOADING_MESSAGE
        }
    }

    suspend fun saveJoke(joke: Joke) {
        if (joke.category.isNotBlank() && joke.question.isNotBlank() && joke.answer.isNotBlank()) {
            try {
                withContext(Dispatchers.IO) {
                    _jokeList.add(joke) // Сохраняем шутку в репозиторий
                }
                _savedSuccessfully.value = true
                _savedSuccessfully.value = false
                isDataChanged = true
                return
            } catch (e: Exception) {
                _error.value = ERROR_SAVING_JOKE
            }
        }
        _error.value = ERROR_FILL_ALL_FIELDS
    }

    companion object {
        private const val ERROR_SAVING_JOKE = "Error saving joke"
        private const val ERROR_FILL_ALL_FIELDS = "Fill all the gapes"
        private const val ERROR_LOADING_MESSAGE = "Error loading jokes"
    }
}