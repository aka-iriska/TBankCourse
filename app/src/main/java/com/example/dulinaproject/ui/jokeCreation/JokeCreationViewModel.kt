package com.example.dulinaproject.ui.jokeCreation

import androidx.lifecycle.ViewModel
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.JokeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class JokeCreationViewModel : ViewModel() {

    private val _savedSuccessfully = MutableStateFlow(false)
    val savedSuccessfully: StateFlow<Boolean> = _savedSuccessfully

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    suspend fun saveJoke(joke: Joke) {
        if (joke.category.isNotBlank() && joke.question.isNotBlank() && joke.answer.isNotBlank()) {
            try {
                withContext(Dispatchers.IO) {
                    JokeData.addJoke(joke) // Сохраняем шутку в репозиторий
                }
                _savedSuccessfully.value = true
                return
            } catch (e: Exception) {
                _error.value = ERROR_SAVING_JOKE
            }
        }
        _error.value = ERROR_FILL_ALL_FIELDS
    }

    companion object {
        private const val ERROR_SAVING_JOKE = "Ошибка сохранения шутки"
        private const val ERROR_FILL_ALL_FIELDS = "Все поля должны быть заполнены"
    }
}