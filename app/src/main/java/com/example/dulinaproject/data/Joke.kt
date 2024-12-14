package com.example.dulinaproject.data

import java.io.Serializable
import java.util.UUID

data class Joke(
    val id: UUID = UUID.randomUUID(),
    val category: String = "",
    val question: String = "",
    val answer: String = ""
) : Serializable