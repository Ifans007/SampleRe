package com.example.sample.ui.screen.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.domain.usecase.reddit.comment.GetCommentListWithSubCommentsByParentIdUseCase
import com.example.sample.domain.usecase.reddit.comment.RefreshPostCommentListUseCase
import com.example.sample.domain.usecase.reddit.subreddit.GetPostByIdUseCase
import com.example.sample.model.comment.CommentModel
import com.example.sample.model.post.PostModel
import com.example.sample.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getCommentListWithSubCommentsByParentIdUseCase: GetCommentListWithSubCommentsByParentIdUseCase,
    private val refreshPostCommentListUseCase: RefreshPostCommentListUseCase,
) : ViewModel() {

    private val postId: String =
        checkNotNull(savedStateHandle.get(Navigator.Screen.Post.KEY_POST_ID))

    private val _isRefreshing = MutableStateFlow(value = false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _post = MutableStateFlow<PostModel?>(value = null)
    val post: StateFlow<PostModel?> = _post.asStateFlow()

    private val _isShowCommentDownload = MutableStateFlow(value = true)
    val isShowCommentDownload: StateFlow<Boolean> = _isShowCommentDownload.asStateFlow()

    private val _commentList = MutableStateFlow<List<CommentModel>>(value = emptyList())
    val commentList: StateFlow<List<CommentModel>> = _commentList.asStateFlow()

    init {

        viewModelScope.launch {

            getPostById(postId = postId)
            getCommentList(parentId = postId)
            refreshPostCommentList()
        }
    }

    private fun getPostById(postId: String) {

        viewModelScope.launch {

            val paras = GetPostByIdUseCase.Params(
                postId = postId
            )

            getPostByIdUseCase
                .invoke(params = paras)
                .onSuccess {
                    _post.emit(value = it.first())
                }
        }
    }

    fun refreshPostCommentList() {

        viewModelScope.launch {

            val params = RefreshPostCommentListUseCase.Params(
                postId = postId
            )

            refreshPostCommentListUseCase
                .invoke(params = params)
                .onSuccess {
                    _isShowCommentDownload.emit(value = false)
                    getCommentList(parentId = postId)
                }
        }
    }

    private fun getCommentList(parentId: String) {

        viewModelScope.launch {

            val params = GetCommentListWithSubCommentsByParentIdUseCase.Params(
                parentId = parentId
            )

            getCommentListWithSubCommentsByParentIdUseCase
                .invoke(params = params)
                .onSuccess {
                    if (it.isNotEmpty()) _isShowCommentDownload.emit(value = false)
                    _commentList.emit(value = it)
                }
        }
    }

    fun onClickSubComment(parentCommentId: String) {
        getCommentList(parentId = parentCommentId)
    }
}