package com.jeanbarrossilva.tick.app.destination.todos.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.tick.app.destination.destinations.ToDoComposerDestination
import com.jeanbarrossilva.tick.app.destination.destinations.ToDoGroupComposerDestination
import com.jeanbarrossilva.tick.feature.fork.Fork
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Composable
@Destination(route = "to-dos/compose", style = DestinationStyleBottomSheet::class)
internal fun Fork(navigator: DestinationsNavigator, modifier: Modifier = Modifier) {
    Fork(
        onNavigationToToDoComposer = { navigator.navigate(ToDoComposerDestination) },
        onNavigationToGroupComposer = { navigator.navigate(ToDoGroupComposerDestination) },
        modifier
    )
}
