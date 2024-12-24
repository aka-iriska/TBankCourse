package com.example.dulinaproject.data.datasource.remote

import com.example.dulinaproject.data.entity.JokeDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApiService {
    @GET("Any")
    suspend fun getRandomJokes(
        @Query("blacklistFlags") blacklistFlags: String = "nsfw,religious,political,racist,sexist,explicit",
        @Query("type") type: String = "twopart",
        @Query("amount") amount: Int = 10
    ): JokeDTO
}