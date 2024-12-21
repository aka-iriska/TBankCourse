package com.example.dulinaproject.data.db

import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.api.JokeNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class JokeRepository(private val jokeDao: JokeDao, private val networkService: JokeNetwork) {

    suspend fun getUserJokes(): Flow<List<Joke>> {
        return flow {
            val localJokes = jokeDao.getUserJokes().first().map { it.toJoke() }
            emit(localJokes)
        }
    }


    fun getApiJokes(): Flow<List<Joke>> {
        return flow {
            val networkJokes = networkService.fetchApiJokes()
            saveCachedJokes(networkJokes)
            emit(networkJokes)
        }
    }

    fun getCachedJokes(): Flow<List<Joke>> {
        return flow {
            val cachedJokes = jokeDao.getCachedJokes().first().map { it.toJoke() }
            emit(cachedJokes)
        }
    }

    // Сохранить свою шутку
    suspend fun saveUserJoke(joke: Joke) {
        jokeDao.insertUserJoke(joke.toUserJoke())
    }

    private suspend fun saveCachedJokes(jokes: List<Joke>) {
        jokeDao.insertCachedJokes(jokes.map { it.toCachedJoke() })
    }

    // Очистить устаревший кэш
    suspend fun clearOldCache() {
        val expiryTime = System.currentTimeMillis() - 2 * 1000 // каждые 2 минуты кеш очищается
        jokeDao.clearOldCache(expiryTime)
    }
}
