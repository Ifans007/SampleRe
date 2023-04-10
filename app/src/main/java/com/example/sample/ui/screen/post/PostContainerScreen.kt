package com.example.sample.ui.screen.post

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sample.model.comment.CommentModel
import com.example.sample.model.post.PostModel
import com.example.sample.ui.screen.post.details.PostScreen
import com.example.sample.ui.theme.RedditRUFirstTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostRoute() {

    val viewModel = hiltViewModel<PostViewModel>()

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.refreshPostCommentList() })

    val post by viewModel.post.collectAsStateWithLifecycle()
    val isShowCommentDownload by viewModel.isShowCommentDownload.collectAsStateWithLifecycle()
    val commentList by viewModel.commentList.collectAsStateWithLifecycle()

    PostContainerScreen(
        isRefreshing = isRefreshing,
        pullRefreshState = pullRefreshState,
        post = post,
        isShowCommentDownload = isShowCommentDownload,
        commentList = commentList,
        onClickSub = viewModel::onClickSubComment
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostContainerScreen(
    isRefreshing: Boolean,
    pullRefreshState: PullRefreshState,
    post: PostModel?,
    isShowCommentDownload: Boolean,
    commentList: List<CommentModel>,
    onClickSub: (String) -> Unit,
    ) {

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState),
    ) {

        PostScreen(
            post = post,
            isShowCommentDownload = isShowCommentDownload,
            commentList = commentList,
            onClickSub = onClickSub
        )

        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}