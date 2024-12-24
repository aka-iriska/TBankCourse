package com.example.dulinaproject.data.datasource.remote

import com.example.dulinaproject.data.entity.JokeDTO
import com.example.dulinaproject.domain.entity.Joke

class JokeNetwork {
    suspend fun fetchApiJokes(): List<Joke> {
        val response: JokeDTO = RetrofitInstance.api.getRandomJokes()
        val networkJokes = response.data.map { joke ->
            Joke(
                category = joke.category,
                question = joke.setup,
                answer = joke.delivery,
                isFromApi = true
            )
        }
        return networkJokes
    }
}