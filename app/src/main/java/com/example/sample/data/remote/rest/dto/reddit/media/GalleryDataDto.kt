package com.example.sample.data.remote.rest.dto.reddit.media

import kotlinx.serialization.Serializable

@Serializable
data class GalleryDataDto(
    val items: List<ItemDto> = emptyList()
) {

    @Serializable
    data class ItemDto(
        val mediaId: String = "",
        val id: Int = -1
    )
}
