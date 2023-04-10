package com.example.sample.domain.usecase.reddit.subreddit

import com.example.sample.data.local.database.entity.toModel
import com.example.sample.data.repository.post.PostRepository
import com.example.sample.domain.usecase.base.UseCase
import com.example.sample.model.post.PostModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val postRepository: PostRepository
) : UseCase<GetPostByIdUseCase.Params, Flow<PostModel>>() {

    override suspend fun execute(params: Params) =
        postRepository.getPostById(id = params.postId)
            .map { it.toModel() }

    data class Params(
        val postId: String
    )
}