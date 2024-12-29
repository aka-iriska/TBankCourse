package com.example.dulinaproject.domain.entity

import java.io.Serializable
import java.util.UUID

data class Joke(
    val id: UUID = UUID.randomUUID(),
    val category: String = "",
    val question: String = "",
    val answer: String = "",
    val isFromApi: Boolean = false,
) : Serializable