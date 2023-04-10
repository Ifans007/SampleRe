package com.example.sample.data.repository.post

import com.example.sample.data.local.database.dao.PostDao
import com.example.sample.data.local.database.entity.PostEntity
import com.example.sample.data.remote.rest.api.PostApi
import com.example.sample.data.remote.rest.dto.reddit.common.SCommonDataDto
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postApi: PostApi,
    private val postDao: PostDao
) {

    suspend fun getPopularPostList(
        after: String?,
        before: String?,
        limit: Int?
    ): SCommonDataDto {

        return postApi.getPopularPostList(
            after = after,
            before = before,
            limit = limit
        )
    }

    fun getPostPagingSource() = postDao.getPostPagingSource()

    suspend fun insertPostList(postList: List<PostEntity>) =
        postDao.insertPostList(postList = postList)

    fun getPostById(id: String) = postDao.getPostById(id = id)

    suspend fun clearPost() = postDao.clearPosts()
}