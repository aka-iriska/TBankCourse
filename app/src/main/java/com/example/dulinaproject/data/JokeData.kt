package com.example.dulinaproject.data

object JokeData {

    private val data = mutableListOf<Joke>()

    fun addJoke(joke: Joke) {
        data.add(joke)
    }

    fun getJokes(): List<Joke> {
        return data
    }

    fun getJokeByPosition(position: Int): Joke {
        return data[position]
    }
}