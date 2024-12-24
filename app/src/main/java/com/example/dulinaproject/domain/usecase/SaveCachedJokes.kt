package com.example.dulinaproject.domain.usecase

import com.example.dulinaproject.domain.entity.Joke
import com.example.dulinaproject.domain.repository.JokeRepository

class SaveCachedJokes(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(jokes: List<Joke>) {
        return jokeRepository.saveCachedJokes(jokes)
    }
}