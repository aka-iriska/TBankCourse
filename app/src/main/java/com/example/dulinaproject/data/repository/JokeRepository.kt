package com.example.dulinaproject.data.repository

import com.example.dulinaproject.data.datasource.local.LocalDataSource
import com.example.dulinaproject.data.datasource.remote.JokeNetwork
import com.example.dulinaproject.data.mapper.JokeDbModelMapper
import com.example.dulinaproject.domain.entity.Joke
import com.example.dulinaproject.domain.repository.JokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class JokeRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val networkService: JokeNetwork,
    private val jokeDbModelMapper: JokeDbModelMapper
) : JokeRepository {

    override suspend fun getUserJokes(): Flow<List<Joke>> {
        return flow {
            val localJokes =
                localDataSource.getUserJokes().first().map { jokeDbModelMapper.mapUserJoke(it) }
            emit(localJokes)
        }
    }

    override fun getApiJokes(): Flow<List<Joke>> {
        return flow {
            val networkJokes = networkService.fetchApiJokes()
            saveCachedJokes(networkJokes)
            emit(networkJokes)
        }
    }

    override fun getCachedJokes(): Flow<List<Joke>> {
        return flow {
            val cachedJokes =
                localDataSource.getCachedJokes().first().map { jokeDbModelMapper.mapCachedJoke(it) }
            emit(cachedJokes)
        }
    }

    // Сохранить свою шутку
    override suspend fun saveUserJoke(joke: Joke) {
        localDataSource.insertUserJoke(jokeDbModelMapper.mapToUserJoke(joke))
    }

    override suspend fun saveCachedJokes(jokes: List<Joke>) {
        localDataSource.insertCachedJokes(jokes.map { jokeDbModelMapper.mapToCachedJoke(it) })
    }

    // Очистить устаревший кэш
    override suspend fun clearOldCache() {
        val expiryTime = System.currentTimeMillis() - 2 * 1000 // каждые 2 минуты кеш очищается
        localDataSource.clearOldCache(expiryTime)
    }
}
