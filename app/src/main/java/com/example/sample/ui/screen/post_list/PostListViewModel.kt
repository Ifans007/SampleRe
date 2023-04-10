package com.example.sample.ui.screen.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sample.domain.usecase.reddit.subreddit.GetPopularPostPagingDataUseCase
import com.example.sample.model.post.PostModel
import com.example.sample.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPopularPostPagingDataUseCase: GetPopularPostPagingDataUseCase,
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    val postListUiState: Flow<PagingData<PostModel>> =
        getPopularPostPagingDataUseCase
            .invoke(params = Unit)
            .getOrDefault(flowOf(PagingData.empty()))
            .cachedIn(scope = viewModelScope)

    fun onPostClick(id: String) {
        Navigator.perform(
            Navigator.Command.Navigate(
                destination = Navigator.Screen.Post(
                    postId = id
                )
            ),
        )
    }
}