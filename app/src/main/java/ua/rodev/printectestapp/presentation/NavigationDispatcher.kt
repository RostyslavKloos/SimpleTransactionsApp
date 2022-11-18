package ua.rodev.printectestapp.presentation

import androidx.navigation.NavController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult

typealias NavigationCommand = (NavController) -> Unit

interface NavigationDispatcher {

    fun emit(navigationCommand: NavigationCommand): ChannelResult<Unit>
    suspend fun collect(navController: NavController)

    class Main : NavigationDispatcher {
        private val navigationEmitter = Channel<NavigationCommand>(Channel.UNLIMITED)

        override suspend fun collect(navController: NavController) {
            for (command in navigationEmitter)
                command.invoke(navController)
        }

        override fun emit(navigationCommand: NavigationCommand) =
            navigationEmitter.trySend(navigationCommand)
    }
}
