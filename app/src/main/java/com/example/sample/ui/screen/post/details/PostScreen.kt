package com.example.sample.ui.screen.post.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sample.model.comment.CommentModel
import com.example.sample.model.post.PostModel
import com.example.sample.ui.common.CommentItem
import com.example.sample.ui.common.PostItem
import com.example.sample.ui.theme.RedditRUFirstTheme

@Composable
fun PostScreen(
    post: PostModel?,
    isShowCommentDownload: Boolean,
    commentList: List<CommentModel>,
    onClickSub: (String) -> Unit,
) {

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            post?.let {

                PostItem(post = post,)
            }
        }

        if (isShowCommentDownload) item { CircularProgressIndicator() }

        Comment(
            lazyListScope = this,
            commentList = commentList,
            onClickSub = onClickSub
        )
    }
}

private fun Comment(
    lazyListScope: LazyListScope,
    commentList: List<CommentModel>,
    onClickSub: (String) -> Unit,
    startPadding: Int = 0
) {

    commentList.forEach { comment ->

        lazyListScope.item(
            key = comment.id,
        ) {

            Box(
                propagateMinConstraints = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = startPadding.dp)
            ) {

                CommentItem(
                    comment = comment,
                    hasSubCommentList = comment.subCommentList.isNotEmpty(),
                    onClickSub = { onClickSub(comment.id) },
                )
            }
        }

        if (comment.subCommentList.isNotEmpty()) {

            Comment(
                lazyListScope = lazyListScope,
                commentList = comment.subCommentList,
                onClickSub = onClickSub,
                startPadding = startPadding + 16
            )
        }
    }
}

@Preview(device = Devices.PIXEL_2_XL)
@Composable
fun PostBottomSheetScreenPreview() {

    RedditRUFirstTheme {
        PostScreen(
            post = PostModel.SAMPLE,
            isShowCommentDownload = false,
            commentList = emptyList(),
            onClickSub = {},
        )
    }
}