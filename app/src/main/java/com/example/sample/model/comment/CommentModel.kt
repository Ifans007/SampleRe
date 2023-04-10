package com.example.sample.model.comment

data class CommentModel(
    val id: String,
    val parentId: String,
    val author: String,
    val created: Double,
    val ups: Int,
    val body: String,
    val subCommentList: List<CommentModel>
) {

    companion object {
        val SAMPLE = CommentModel(
            id = "",
            parentId = "",
            author = "Author",
            created = 11111.0,
            ups = 11111,
            body = "Body Body Body Body Body",
            subCommentList = emptyList(),
        )
    }
}