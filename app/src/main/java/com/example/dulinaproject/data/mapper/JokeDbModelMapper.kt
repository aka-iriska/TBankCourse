package com.example.dulinaproject.data.mapper

import com.example.dulinaproject.data.entity.CachedJoke
import com.example.dulinaproject.data.entity.UserJoke
import com.example.dulinaproject.domain.entity.Joke

class JokeDbModelMapper {

    fun mapUserJoke(userJoke: UserJoke): Joke {
        return with(userJoke) {
            Joke(
                category = category,
                question = question,
                answer = answer,
                isFromApi = false // Указывает, что это пользовательская шутка
            )
        }
    }

    fun mapCachedJoke(cachedJoke: CachedJoke): Joke {
        return with(cachedJoke) {
            Joke(
                category = category,
                question = question,
                answer = answer,
                isFromApi = true // Указывает, что это шутка из API
            )
        }
    }

    fun mapToUserJoke(joke: Joke): UserJoke {
        return with(joke) {
            UserJoke(
                id = 0,
                category = category,
                question = question,
                answer = answer,
            )
        }
    }

    fun mapToCachedJoke(joke: Joke): CachedJoke {
        return with(joke) {
            CachedJoke(
                id = 0,
                category = category,
                question = question,
                answer = answer,
            )
        }
    }
}