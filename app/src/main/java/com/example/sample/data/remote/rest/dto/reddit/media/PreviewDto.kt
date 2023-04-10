package com.example.sample.data.remote.rest.dto.reddit.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import timber.log.Timber

@Serializable
data class PreviewDto(
    val images: List<SourceDto>? = null,
    @SerialName("reddit_video_preview")
    val videoPreview: VideoPreviewDto? = null
) {

    @Serializable
    data class SourceDto(
        val source: UrlDto = UrlDto()
    ) {

        @Serializable
        data class UrlDto(
            val url: String = ""
        )
    }

    @Serializable
    data class VideoPreviewDto(
        @SerialName("is_gif")
        val isGif: Boolean = true,
        @SerialName("hls_url")
        val hlsUrl: String = ""
    )
}
