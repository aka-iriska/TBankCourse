package com.example.dulinaproject.ui.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.api.JokeNetwork
import com.example.dulinaproject.data.db.JokeDatabase
import com.example.dulinaproject.data.db.JokeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class JokeListViewModel : ViewModel() {

    private val repository: JokeRepository by lazy {
        JokeRepository(
            JokeDatabase.INSTANCE.jokeDao(),
            JokeNetwork()
        )
    }

    private val _jokes =
        MutableStateFlow((listOf<Joke>())) // дабы обновлялся State при getJokesList
    val jokes: StateFlow<List<Joke>> = _jokes

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _savedSuccessfully = MutableStateFlow(false)
    val savedSuccessfully: StateFlow<Boolean> = _savedSuccessfully

    private var hasCalledFetch = false

    fun loadJokes() {
        if (!hasCalledFetch) {
            hasCalledFetch = true
            viewModelScope.launch {
                _isLoading.value = true
                repository.clearOldCache()
                repository.getUserJokes()
                    .collect {
                        _jokes.value = it
                    }
                runCatching {
                    repository.getApiJokes().collect {
                        _jokes.value += it
                    }
                }.onFailure {
                    repository.getCachedJokes().collect { list ->
                        if (list.isEmpty())
                            _error.value = ERROR_LOADING_ALL_MESSAGE
                        else _jokes.value += list
                    }
                    _error.value = ERROR_LOADING_MESSAGE
                    _error.value = ""
                }
                _isLoading.value = false
                hasCalledFetch = true
            }
        }
    }

    fun paginationLoadJokes(onComplete: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                repository.getApiJokes().collect {
                    _jokes.value += it
                }
            }
                .onFailure {
                    _error.value = ERROR_LOADING_MESSAGE
                    _error.value = ""
                }
            onComplete()
        }
    }

    suspend fun saveJoke(joke: Joke) {
        if (joke.category.isNotBlank() && joke.question.isNotBlank() && joke.answer.isNotBlank()) {
            runCatching {
                repository.saveUserJoke(joke)
            }
                .onSuccess {
                    _savedSuccessfully.value = true
                    _savedSuccessfully.value = false
                    hasCalledFetch = false
                }
                .onFailure { _error.value = ERROR_SAVING_JOKE }
            return
        }
        _error.value = ERROR_FILL_ALL_FIELDS
    }

    companion object {
        private const val ERROR_SAVING_JOKE = "Error saving joke"
        private const val ERROR_FILL_ALL_FIELDS = "Fill all the gapes"
        private const val ERROR_LOADING_MESSAGE = "Error loading jokes from API"
        private const val ERROR_LOADING_ALL_MESSAGE = "Nigher API nor cached jokes"
    }
}