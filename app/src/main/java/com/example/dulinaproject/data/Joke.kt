package com.example.dulinaproject.data

import java.util.UUID

data class Joke(
    val id: UUID,
    val category: String,
    val question: String,
    val answer: String
)