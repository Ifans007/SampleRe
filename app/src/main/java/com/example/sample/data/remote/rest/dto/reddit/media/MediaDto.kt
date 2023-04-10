package com.example.sample.data.remote.rest.dto.reddit.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDto(
    @SerialName("reddit_video")
    val video: VideoDto = VideoDto()
)
