package com.example.dulinaproject.domain.usecase

import com.example.dulinaproject.domain.entity.Joke
import com.example.dulinaproject.domain.repository.JokeRepository
import kotlinx.coroutines.flow.Flow

class GetCachedJokes(private val jokeRepository: JokeRepository) {

    operator fun invoke(): Flow<List<Joke>> {
        return jokeRepository.getCachedJokes()
    }
}