package com.example.dulinaproject.domain.usecase

import com.example.dulinaproject.domain.entity.Joke
import com.example.dulinaproject.domain.repository.JokeRepository

class SaveUserJokes(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(joke: Joke) {
        return jokeRepository.saveUserJoke(joke)
    }
}