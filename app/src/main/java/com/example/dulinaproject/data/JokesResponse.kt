package com.example.dulinaproject.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokesResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("amount")
    val amount: Int,
    @SerialName("jokes")
    val data: List<NetworkJokes>,
)

@Serializable
data class NetworkJokes(
    @SerialName("category")
    val category: String,
    @SerialName("type")
    val type: String,
    @SerialName("setup")
    val setup: String,
    @SerialName("delivery")
    val delivery: String,
    @SerialName("flags")
    val flags: Flags,
    @SerialName("safe")
    val safe: Boolean,
    @SerialName("id")
    val id: Int,
    @SerialName("lang")
    val lang: String,
)

@Serializable
data class Flags(
    @SerialName("nsfw") val nsfw: Boolean,
    @SerialName("religious") val religious: Boolean,
    @SerialName("political") val political: Boolean,
    @SerialName("racist") val racist: Boolean,
    @SerialName("sexist") val sexist: Boolean,
    @SerialName("explicit") val explicit: Boolean,
)
