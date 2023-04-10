package com.example.sample.domain.usecase.reddit.comment

import com.example.sample.data.local.database.entity.toModel
import com.example.sample.data.remote.rest.dto.reddit.common.ContentType
import com.example.sample.domain.usecase.base.UseCase
import com.example.sample.model.comment.CommentModel
import javax.inject.Inject

class GetCommentListWithSubCommentsByParentIdUseCase @Inject constructor(
    private val getCommentListByParentIdUseCase: GetCommentListByParentIdUseCase
) : UseCase<GetCommentListWithSubCommentsByParentIdUseCase.Params, List<CommentModel>>() {

    private var mainCommentList = listOf<CommentModel>()

    override suspend fun execute(params: Params): List<CommentModel> {

        val p = GetCommentListByParentIdUseCase.Params(
            parentId = params.parentId
        )

        getCommentListByParentIdUseCase
            .invoke(params = p)
            .map {
                it.map {
                    it.toModel()
                }
            }
            .onSuccess {

                if (mainCommentList.isEmpty() || params.parentId.contains(ContentType.POST.type)) {
                    mainCommentList = it
                } else {

                    checkSubComment(
                        mainRun = true,
                        parentId = params.parentId,
                        commentList = mainCommentList,
                        subCommentList = it
                    )
                }
            }

        return mainCommentList
    }

    private fun checkSubComment(
        mainRun: Boolean,
        parentId: String,
        commentList: List<CommentModel>,
        subCommentList: List<CommentModel>
    ): Pair<List<CommentModel>, Boolean> {

        var isFindParentComment = false

        val commentListWithSubComments = commentList.map { comment ->

            if (!isFindParentComment) {

                if (comment.id == parentId) {

                    isFindParentComment = true

                    if (comment.subCommentList.isEmpty()) comment.copy(subCommentList = subCommentList)
                    else comment.copy(subCommentList = emptyList())

                } else {

                    if (comment.subCommentList.isNotEmpty()) {

                        val result =
                            checkSubComment(
                                mainRun = false,
                                parentId = parentId,
                                commentList = comment.subCommentList,
                                subCommentList = subCommentList
                            )

                        isFindParentComment = result.second
                        comment.copy(subCommentList = result.first)

                    } else {

                        comment
                    }
                }
            } else {

                comment
            }
        }

        if(mainRun) mainCommentList = commentListWithSubComments

        return Pair(commentListWithSubComments, isFindParentComment)
    }

    data class Params(
        val parentId: String
    )
}