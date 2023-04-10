package com.example.sample.data.remote.rest.dto.reddit.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    @SerialName("hls_url")
    val hlsUrl: String = ""

)
