package com.example.dulinaproject.data.datasource.local

import com.example.dulinaproject.data.entity.CachedJoke
import com.example.dulinaproject.data.entity.UserJoke
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val dao: JokeDao) : LocalDataSource {

    override suspend fun insertUserJoke(joke: UserJoke) {
        return dao.insertUserJoke(joke)
    }

    override fun getUserJokes(): Flow<List<UserJoke>> {
        return dao.getUserJokes()
    }

    override suspend fun insertCachedJokes(jokes: List<CachedJoke>) {
        return dao.insertCachedJokes(jokes)
    }

    override fun getCachedJokes(): Flow<List<CachedJoke>> {
        return dao.getCachedJokes()
    }

    override suspend fun clearOldCache(expiryTime: Long) {
        return dao.clearOldCache(expiryTime)
    }

    override suspend fun clearAllCache() {
        return dao.clearAllCache()
    }
}