package com.jeanbarrossilva.tick.app.destination.todos.compose.group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import com.jeanbarrossilva.tick.feature.composer.group.ToDoGroupComposer
import com.jeanbarrossilva.tick.feature.composer.group.ToDoGroupComposerViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.compose.koinInject

@Composable
@Destination(route = "to-dos/compose/group")
internal fun ToDoGroupComposer(navigator: DestinationsNavigator, modifier: Modifier = Modifier) {
    val editor = koinInject<ToDoEditor>()
    val viewModelFactory = ToDoGroupComposerViewModel.createFactory(editor)
    val viewModel = viewModel<ToDoGroupComposerViewModel>(factory = viewModelFactory)
    val onBackwardsNavigation by rememberUpdatedState<() -> Unit> { navigator.popBackStack() }

    ToDoGroupComposer(viewModel, onBackwardsNavigation, onDone = onBackwardsNavigation, modifier)
}
