package com.example.sample.domain.usecase.reddit.comment

import com.example.sample.data.remote.rest.dto.reddit.comment.toEntity
import com.example.sample.data.remote.rest.dto.reddit.common.ContentType
import com.example.sample.data.remote.rest.dto.reddit.common.SCommonDataDto
import com.example.sample.data.remote.rest.dto.reddit.common.findComments
import com.example.sample.data.repository.comment.CommentRepository
import com.example.sample.domain.usecase.base.UseCase
import javax.inject.Inject

class RefreshPostCommentListUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) : UseCase<RefreshPostCommentListUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {

        val dataList = with(receiver = params) {

            commentRepository.getPostCommentList(
                postId = postId.removePrefix(ContentType.POST.type),
                depth = depth,
                limit = limit
            )
        }

        dataList.forEach {

            if (it is SCommonDataDto.Listing) {

                val commentDaoList = it.findComments()
                val commentEntityList = commentDaoList.map { it.data.toEntity() }

                commentRepository.insertCommentList(commentList = commentEntityList)
            }
        }
    }

    data class Params(
        val postId: String,
        val depth: Int? = null,
        val limit: Int? = null
    )
}