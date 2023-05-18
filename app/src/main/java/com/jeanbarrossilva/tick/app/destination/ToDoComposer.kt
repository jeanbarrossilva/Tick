package com.jeanbarrossilva.tick.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.tick.app.destination.destinations.GroupsDestination
import com.jeanbarrossilva.tick.app.destination.groups.GroupsArgument
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.feature.composer.todo.ToDoComposer
import com.jeanbarrossilva.tick.feature.composer.todo.ToDoComposerViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.compose.koinInject

@Composable
@Destination(route = "to-dos/compose/to-do")
internal fun ToDoComposer(
    navigator: DestinationsNavigator,
    groupsResultRecipient: ResultRecipient<GroupsDestination, ToDoGroup>,
    modifier: Modifier = Modifier
) {
    val repository = koinInject<ToDoRepository>()
    val editor = koinInject<ToDoEditor>()
    val viewModelFactory = ToDoComposerViewModel.createFactory(repository, editor)
    val viewModel = viewModel<ToDoComposerViewModel>(factory = viewModelFactory)

    groupsResultRecipient.onNavResult { result ->
        if (result is NavResult.Value) {
            viewModel.setGroup(result.value)
        }
    }

    ToDoComposer(
        viewModel,
        onBackwardsNavigation = navigator::popBackStack,
        onNavigationToGroups = { groups ->
            val argument = GroupsArgument(groups)
            val destination = GroupsDestination(argument)
            navigator.navigate(destination)
        },
        modifier
    )
}
