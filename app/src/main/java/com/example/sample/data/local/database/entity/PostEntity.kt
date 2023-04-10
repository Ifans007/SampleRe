package com.example.sample.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sample.domain.type.reddit.PostHintEnumType
import com.example.sample.model.post.PostModel

@Entity
data class PostEntity(
    @PrimaryKey
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
)

fun PostEntity.toModel(): PostModel {

    return PostModel(
        id = id,
        subreddit = subreddit,
        ups = ups,
        title = title,
        postHint = postHint,
        selftext = selftext,
        isVideo = isVideo,
        image = image,
        isGif = isGif,
        hlsUrl = hlsUrl,
        isGallery = isGallery,
        created = created
    )
}
