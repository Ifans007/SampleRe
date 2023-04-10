package com.example.sample.domain.usecase.reddit.comment

import com.example.sample.data.local.database.entity.CommentEntity
import com.example.sample.data.repository.comment.CommentRepository
import com.example.sample.domain.usecase.base.UseCase
import javax.inject.Inject

class GetCommentListByParentIdUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) : UseCase<GetCommentListByParentIdUseCase.Params, List<CommentEntity>>() {

    override suspend fun execute(params: Params) = commentRepository.getCommentListByParentId(id = params.parentId)

    data class Params(
        val parentId: String
    )
}