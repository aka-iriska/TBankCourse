package com.example.dulinaproject.domain.repository

import com.example.dulinaproject.domain.entity.Joke
import kotlinx.coroutines.flow.Flow

interface JokeRepository {

    suspend fun getUserJokes(): Flow<List<Joke>>

    fun getApiJokes(): Flow<List<Joke>>

    fun getCachedJokes(): Flow<List<Joke>>

    suspend fun saveUserJoke(joke: Joke)

    suspend fun saveCachedJokes(jokes: List<Joke>)

    suspend fun clearOldCache()
}