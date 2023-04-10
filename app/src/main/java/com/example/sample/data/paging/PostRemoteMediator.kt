package com.example.sample.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.sample.data.local.database.entity.PostEntity
import com.example.sample.data.remote.rest.dto.reddit.common.SCommonDataDto
import com.example.sample.data.remote.rest.dto.reddit.post.toEntity
import com.example.sample.data.repository.post.PostRepository
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator @Inject constructor(
    private val postRepository: PostRepository
) : RemoteMediator<Int, PostEntity>() {

    private var mAfter: String? = null

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {

        val after = when (loadType) {
            LoadType.APPEND -> mAfter
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(true)
        }

        try {

            val response = postRepository.getPopularPostList(
                after = after,
                before = null,
                limit = null
            )

            if (loadType == LoadType.REFRESH) postRepository.clearPost()

            val postList = if (response is SCommonDataDto.Listing) {

                mAfter = response.data.after

                response.data.children.mapNotNull {
                    if (it is SCommonDataDto.Post) it.data.toEntity()
                    else null
                }
            } else {

                emptyList()
            }

            postRepository.insertPostList(postList = postList)

            val endOfPaginationReached = postList.isEmpty()

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {

            return MediatorResult.Error(e)
        }
    }
}