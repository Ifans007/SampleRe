package com.example.sample.ui.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object Navigator {
    @Suppress("ObjectPropertyName")
    private val _commands = Channel<Command>(Channel.UNLIMITED)
    val commands = _commands.receiveAsFlow()

    fun perform(vararg command: Command) {
        command.forEach { _commands.trySend(it) }
    }

    sealed class Screen(val name: String) {

        /** The navigation route with the arguments */
        open val route: String = name

        /** Placeholder string, denoting the parameter names and order */
        open val path: String = name

        object PostList : Screen("post_list")

        class Post(
            postId: String,
        ) : Screen("post") {
            override val route: String = "$name/$postId"
            override val path = "$name/{$KEY_POST_ID}"

            companion object {
                const val KEY_POST_ID = "postId"
            }
        }
    }

    sealed class Command {

        object PopUp : Command()

        class PopUpTo(val destination: Screen, val isInclusive: Boolean) : Command()

        class Navigate(
            val destination: Screen,
            val expectedBackstackScreen: Screen? = null
        ) : Command()

        object PopUpAll : Command()

        object Finish : Command()
    }
}
