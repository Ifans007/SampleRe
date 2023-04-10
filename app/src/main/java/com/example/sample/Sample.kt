package com.example.sample

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.sample.ui.navigation.NavigationComponent
import com.example.sample.ui.navigation.Navigator

@Composable
fun SampleApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavigationComponent(
        navController = navController,
        navigator = Navigator,
        startDestination = viewModel.startDestination
    )
}
