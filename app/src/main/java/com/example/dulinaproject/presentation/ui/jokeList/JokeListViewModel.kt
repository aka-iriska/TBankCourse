package com.example.dulinaproject.presentation.ui.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulinaproject.domain.entity.Joke
import com.example.dulinaproject.domain.usecase.ClearOldCacheUseCase
import com.example.dulinaproject.domain.usecase.GetApiJokesUseCase
import com.example.dulinaproject.domain.usecase.GetCachedJokesUseCase
import com.example.dulinaproject.domain.usecase.GetUserJokesUseCase
import com.example.dulinaproject.domain.usecase.SaveUserJokesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class JokeListViewModel(
    private val getApiJokes: GetApiJokesUseCase,
    private val getUserJokesUseCase: GetUserJokesUseCase,
    private val getCachedJokes: GetCachedJokesUseCase,
    private val saveUserJokes: SaveUserJokesUseCase,
    private val clearOldCache: ClearOldCacheUseCase,

    ) : ViewModel() {

    /*private val repository: JokeRepository by lazy {
        JokeRepositoryImpl(
            JokeDatabase.INSTANCE.jokeDao(),
            JokeNetwork(),

        )
    }*/

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
                clearOldCache()
                getUserJokesUseCase()
                    .collect {
                        _jokes.value = it
                    }
                runCatching {
                    getApiJokes().collect {
                        _jokes.value += it
                    }
                }.onFailure {
                    getCachedJokes().collect { list ->
                        if (list.isEmpty())
                            _error.value = ERROR_LOADING_ALL_MESSAGE
                        else _jokes.value += list
                    }
                    _error.value = ERROR_LOADING_MESSAGE
                    _error.value = ""
                }
                _isLoading.value = false
            }
        }
    }

    fun paginationLoadJokes(onComplete: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                getApiJokes().collect {
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
                saveUserJokes(joke)
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