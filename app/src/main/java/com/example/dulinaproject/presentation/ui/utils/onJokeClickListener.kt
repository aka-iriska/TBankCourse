package com.example.dulinaproject.presentation.ui.utils

import com.example.dulinaproject.domain.entity.Joke

interface OnJokeClickListener {
    fun onJokeClick(joke: Joke)
}
