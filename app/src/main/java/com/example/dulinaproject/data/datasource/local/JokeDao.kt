package com.example.dulinaproject.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dulinaproject.data.entity.CachedJoke
import com.example.dulinaproject.data.entity.UserJoke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    // Для таблицы user_joke
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserJoke(joke: UserJoke)

    @Query("SELECT * FROM user_joke ORDER BY createdAt DESC")
    fun getUserJokes(): Flow<List<UserJoke>>

    // Для таблицы cached_joke
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedJokes(jokes: List<CachedJoke>)

    @Query("SELECT * FROM cached_joke ORDER BY createdAt DESC")
    fun getCachedJokes(): Flow<List<CachedJoke>>

    @Query("DELETE FROM cached_joke WHERE createdAt < :expiryTime")
    suspend fun clearOldCache(expiryTime: Long)

    @Query("DELETE FROM cached_joke")
    suspend fun clearAllCache()
}