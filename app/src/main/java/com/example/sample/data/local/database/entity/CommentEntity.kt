package com.example.sample.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sample.model.comment.CommentModel

@Entity
data class CommentEntity(
    @PrimaryKey
    val id: String,
    val parentId: String,
    val author: String,
    val created: Double,
    val ups: Int,
    val body: String
)

fun CommentEntity.toModel(): CommentModel {

    return CommentModel(
        id = id,
        parentId = parentId,
        author = author,
        created = created,
        ups = ups,
        body = body,
        subCommentList = emptyList()
    )
}