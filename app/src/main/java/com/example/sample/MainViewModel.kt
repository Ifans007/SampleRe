package com.example.sample

import androidx.lifecycle.ViewModel
import com.example.sample.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    val startDestination = Navigator.Screen.PostList
}