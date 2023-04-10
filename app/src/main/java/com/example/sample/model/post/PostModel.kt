package com.example.sample.model.post

import com.example.sample.domain.type.reddit.PostHintEnumType

data class PostModel(
    val id: String,
    val subreddit: String,
    val ups: Int,
    val title: String,
    val postHint: PostHintEnumType,
    val selftext: String,
    val isVideo: Boolean,
    val image: String?,
    val isGif: Boolean,
    val hlsUrl: String,
    val isGallery: Boolean,
    val created: Double
) {

    companion object {
        val SAMPLE = PostModel(
            id = "",
            subreddit = "Subreddit",
            ups = 1111,
            title = "Title",
            postHint = PostHintEnumType.UNKNOWN,
            selftext = "Selftext Selftext Selftext Selftext Selftext Selftext",
            isVideo = false,
            image = "",
            isGif = false,
            hlsUrl = "",
            isGallery = false,
            created = 1.0
        )
    }
}
