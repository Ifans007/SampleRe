package com.example.sample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sample.ui.screen.post.PostRoute
import com.example.sample.ui.screen.post_list.PostListRoute
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator,
    startDestination: Navigator.Screen,
) {

    LaunchedEffect(Unit) {
        navigator.commands
            .onEach { handleCommand(it, navController) }
            .launchIn(this)
    }

    NavHost(navController = navController, startDestination = startDestination.path) {

        composable(Navigator.Screen.PostList.path) {
            PostListRoute()
        }
        composable(
            route = Navigator.Screen.Post("").path,
            arguments = listOf(
                navArgument(Navigator.Screen.Post.KEY_POST_ID) {
                    type = NavType.StringType
                    nullable = false
                })
        ) {
            PostRoute()
        }
    }
}

private fun handleCommand(
    it: Navigator.Command,
    navController: NavHostController,
) {
    when (it) {
        is Navigator.Command.PopUp -> navController.popBackStack()
        is Navigator.Command.PopUpTo -> navController.popBackStack(
            it.destination.path,
            it.isInclusive
        )
        is Navigator.Command.Navigate -> {
            if (it.expectedBackstackScreen == null
                || it.expectedBackstackScreen.route == navController.currentBackStackEntry?.destination?.route
            )
                navController.navigate(it.destination.route)
            else
                Timber.i("Dropping navigation command $it: expected backstack screen ${it.expectedBackstackScreen} but was ${navController.currentBackStackEntry?.destination?.route}")
        }
        is Navigator.Command.PopUpAll -> while (navController.popBackStack()) Unit
        is Navigator.Command.Finish -> {
            while (navController.popBackStack()) Unit
            navController.navigateUp()
        }
    }
}
