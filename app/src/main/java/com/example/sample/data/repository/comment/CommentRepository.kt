package com.example.sample.data.repository.comment

import com.example.sample.data.local.database.dao.CommentDao
import com.example.sample.data.local.database.entity.CommentEntity
import com.example.sample.data.remote.rest.api.CommentApi
import com.example.sample.data.remote.rest.dto.reddit.comment.toEntity
import com.example.sample.data.remote.rest.dto.reddit.common.SCommonDataDto
import com.example.sample.data.remote.rest.dto.reddit.common.findComments
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val commentApi: CommentApi,
    private val commentDao: CommentDao
) {

    suspend fun getPostCommentList(
        postId: String,
        depth: Int?,
        limit: Int?
    ): List<SCommonDataDto>{

        return commentApi.getPostCommentList(
            postId = postId,
            depth = depth,
            limit = limit
        )
    }

    suspend fun insertCommentList(commentList: List<CommentEntity>) =
        commentDao.insertCommentList(commentList = commentList)

    fun getCommentListByParentId(id: String) = commentDao.getCommentListByParentId(id = id)
}