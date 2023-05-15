package com.jeanbarrossilva.tick.app.extensions

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import com.jeanbarrossilva.tick.feature.composer.COMPOSER_ROUTE
import com.jeanbarrossilva.tick.feature.todos.TO_DOS_ROUTE
import com.jeanbarrossilva.tick.feature.todos.ToDos
import com.jeanbarrossilva.tick.feature.todos.ToDosViewModel
import org.koin.compose.koinInject

/**
 * Adds [ToDos] to the [NavGraphBuilder].
 *
 * @param navController [NavController] to navigate with.
 * @param onBottomAreaAvailabilityChange Callback run whenever the bottom area is free of content.
 * @param modifier [Modifier] to be applied to [ToDos].
 **/
internal fun NavGraphBuilder.toDos(
    navController: NavController,
    onBottomAreaAvailabilityChange: (isAvailable: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(TO_DOS_ROUTE) {
        val repository = koinInject<ToDoGroupRepository>()
        val editor = koinInject<ToDoEditor>()
        val viewModelFactory =
            remember(repository) { ToDosViewModel.createFactory(repository, editor) }

        ToDos(
            viewModel(factory = viewModelFactory),
            onNavigationToComposer = { navController.navigate(COMPOSER_ROUTE) },
            onBottomAreaAvailabilityChange,
            modifier
        )
    }
}
