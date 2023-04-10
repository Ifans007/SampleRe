package com.example.sample.data.remote.rest.api

import com.example.sample.data.remote.rest.dto.reddit.common.SCommonDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("r/popular/hot.json?raw_json=1")
    suspend fun getPopularPostList(
        @Query("after")
        after: String?,
        @Query("before")
        before: String?,
        @Query("limit")
        limit: Int?,
    ): SCommonDataDto
}
