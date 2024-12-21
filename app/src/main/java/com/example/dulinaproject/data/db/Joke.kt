package com.example.dulinaproject.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dulinaproject.data.Joke
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user_joke")
data class UserJoke (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerialName("category") val category: String = "",
    @SerialName("question") val question: String = "",
    @SerialName("answer") val answer: String = "",
    @SerialName("created_at") val createdAt: Long = System.currentTimeMillis()
)

@Serializable
@Entity(tableName = "cached_joke")
data class CachedJoke (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerialName("category") val category: String = "",
    @SerialName("question") val question: String = "",
    @SerialName("answer") val answer: String = "",
    @SerialName("created_at") val createdAt: Long = System.currentTimeMillis()
)

fun UserJoke.toJoke(): Joke {
    return Joke(
        category = this.category,
        question = this.question,
        answer = this.answer,
        isFromApi = false // Указывает, что это пользовательская шутка
    )
}

fun CachedJoke.toJoke(): Joke {
    return Joke(
        category = this.category,
        question = this.question,
        answer = this.answer,
        isFromApi = true // Указывает, что это шутка из API
    )
}

fun Joke.toUserJoke(): UserJoke {
    return UserJoke(
        id = 0,
        category = this.category,
        question = this.question,
        answer = this.answer,
    )
}

fun Joke.toCachedJoke(): CachedJoke {
    return CachedJoke(
        id = 0,
        category = this.category,
        question = this.question,
        answer = this.answer,
    )
}
