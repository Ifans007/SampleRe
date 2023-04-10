package com.example.sample.data.remote.rest.dto.reddit.common

import kotlinx.serialization.Serializable

@Serializable
data class DataListDto(
    val after: String? = null,
    val children: List<SCommonDataDto> = emptyList(),
    val before: String? = null
)