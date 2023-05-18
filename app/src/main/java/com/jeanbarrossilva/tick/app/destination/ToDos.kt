package com.jeanbarrossilva.tick.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.tick.app.destination.destinations.ForkDestination
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.feature.todos.ToDos
import com.jeanbarrossilva.tick.feature.todos.ToDosViewModel
import com.jeanbarrossilva.tick.platform.theme.change.OnBottomAreaAvailabilityChangeListener
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.compose.koinInject

@Composable
@Destination
@RootNavGraph(start = true)
internal fun ToDos(
    navigator: DestinationsNavigator,
    onBottomAreaAvailabilityChangeListener: OnBottomAreaAvailabilityChangeListener,
    modifier: Modifier = Modifier
) {
    val repository = koinInject<ToDoRepository>()
    val editor = koinInject<ToDoEditor>()
    val viewModelFactory = ToDosViewModel.createFactory(repository, editor)
    val viewModel = viewModel<ToDosViewModel>(factory = viewModelFactory)

    ToDos(
        viewModel,
        onNavigationToFork = { navigator.navigate(ForkDestination) },
        onBottomAreaAvailabilityChangeListener,
        modifier
    )
}
