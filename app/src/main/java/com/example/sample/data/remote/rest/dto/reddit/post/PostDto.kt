package com.example.sample.data.remote.rest.dto.reddit.post

import com.example.sample.data.local.database.entity.PostEntity
import com.example.sample.data.remote.rest.dto.reddit.common.ContentType
import com.example.sample.data.remote.rest.dto.reddit.media.GalleryDataDto
import com.example.sample.data.remote.rest.dto.reddit.media.MediaDto
import com.example.sample.data.remote.rest.dto.reddit.media.PreviewDto
import com.example.sample.domain.type.base.GetEnumType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String = "",
    @SerialName("subreddit_name_prefixed")
    val subreddit: String = "",
    val ups: Int = -1,
    val title: String = "",
    @SerialName("post_hint")
    val postHint: String? = null,
    val selftext: String = "",
    @SerialName("is_video")
    val isVideo: Boolean = false,
    val media: MediaDto? = null,
    val preview: PreviewDto? = null,
    @SerialName("gallery_data")
    val galleryData: GalleryDataDto? = null,
    val created: Double = -1.0
)

fun PostDto.toEntity(): PostEntity {

    return PostEntity(
        id = "${ContentType.POST.type}$id",
        subreddit = subreddit,
        ups = ups,
        title = title,
        postHint = GetEnumType.getType(type = postHint),
        selftext = selftext,
        isVideo = isVideo,
        image = preview?.images?.firstOrNull()?.source?.url ?: "",
        isGif = preview?.videoPreview?.isGif ?: false,
        hlsUrl = media?.video?.hlsUrl ?: preview?.videoPreview?.hlsUrl ?: "",
        isGallery = galleryData != null,
        created = created
    )
}