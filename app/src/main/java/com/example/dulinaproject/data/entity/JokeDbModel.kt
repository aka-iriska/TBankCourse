package com.example.dulinaproject.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user_joke")
data class UserJoke(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerialName("category") val category: String = "",
    @SerialName("question") val question: String = "",
    @SerialName("answer") val answer: String = "",
    @SerialName("created_at") val createdAt: Long = System.currentTimeMillis()
)

@Serializable
@Entity(tableName = "cached_joke")
data class CachedJoke(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerialName("category") val category: String = "",
    @SerialName("question") val question: String = "",
    @SerialName("answer") val answer: String = "",
    @SerialName("created_at") val createdAt: Long = System.currentTimeMillis()
)


