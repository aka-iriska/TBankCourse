package com.example.dulinaproject.domain.usecase

import com.example.dulinaproject.domain.repository.JokeRepository

class ClearOldCacheUseCase(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke() {
        return jokeRepository.clearOldCache()
    }
}