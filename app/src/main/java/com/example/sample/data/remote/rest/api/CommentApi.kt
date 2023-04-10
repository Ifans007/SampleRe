package com.example.sample.data.remote.rest.api

import com.example.sample.data.remote.rest.dto.reddit.common.SCommonDataDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApi {

    @GET("comments/{postId}.json")
    suspend fun getPostCommentList(
        @Path("postId")
        postId: String,
        @Query("depth")
        depth: Int?,
        @Query("limit")
        limit: Int?,
    ): List<SCommonDataDto>
}
