package com.example.sample.ui.screen.post_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.sample.ui.common.PostItem
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.sample.model.post.PostModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostListRoute() {

    val viewModel = hiltViewModel<PostListViewModel>()

    val postList = viewModel.postListUiState.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { postList.refresh() })

    PostListScreen(
        isRefreshing = isRefreshing,
        pullRefreshState = pullRefreshState,
        postList = postList,
        onPostClick = viewModel::onPostClick
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PostListScreen(
    isRefreshing: Boolean,
    postList: LazyPagingItems<PostModel>,
    pullRefreshState: PullRefreshState,
    onPostClick: (String) -> Unit,
) {

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState),
    ) {

        LazyColumn {

            items(
                items = postList,
                key = { it.id }
            ) { post ->

                post?.let {

                    Surface(onClick = { onPostClick(post.id) }) {
                        PostItem(post = post)
                    }
                }
            }
        }

        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}