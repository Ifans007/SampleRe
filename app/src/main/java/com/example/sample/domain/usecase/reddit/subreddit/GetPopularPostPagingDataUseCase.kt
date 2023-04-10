package com.example.sample.domain.usecase.reddit.subreddit

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.sample.data.local.database.entity.toModel
import com.example.sample.data.paging.PostRemoteMediator
import com.example.sample.data.repository.post.PostRepository
import com.example.sample.domain.usecase.base.FlowUseCase
import com.example.sample.model.post.PostModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularPostPagingDataUseCase @Inject constructor(
    private val postRepository: PostRepository
) : FlowUseCase<Unit, PagingData<PostModel>>() {

    @OptIn(ExperimentalPagingApi::class)
    override fun execute(params: Unit) =
        Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = true,

            ),
            pagingSourceFactory = { postRepository.getPostPagingSource() },
            remoteMediator = PostRemoteMediator(postRepository = postRepository)
        )
            .flow
            .map { pagingData ->
                pagingData.map { postEntity ->
                    postEntity.toModel()
                }
            }
}