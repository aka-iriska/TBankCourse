package com.example.dulinaproject.data.api

import com.example.dulinaproject.data.Joke

class JokeNetwork {
    suspend fun fetchApiJokes(): List<Joke> {
        val response: JokesResponse = RetrofitInstance.api.getRandomJokes()
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