package com.example.dulinaproject.data.datasource.local

import com.example.dulinaproject.data.entity.CachedJoke
import com.example.dulinaproject.data.entity.UserJoke
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertUserJoke(joke: UserJoke)

    fun getUserJokes(): Flow<List<UserJoke>>

    suspend fun insertCachedJokes(jokes: List<CachedJoke>)

    fun getCachedJokes(): Flow<List<CachedJoke>>

    suspend fun clearOldCache(expiryTime: Long)

    suspend fun clearAllCache()
}