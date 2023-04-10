package com.example.sample.data.remote.rest.dto.reddit.comment

import kotlinx.serialization.Serializable

@Serializable
data class MoreDto(
    val id: String,
    val count: Int,
)