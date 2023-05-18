package com.jeanbarrossilva.tick.app.destination.groups

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.groups.Groups
import com.jeanbarrossilva.tick.feature.groups.GroupsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Composable
@Destination(route = "to-dos/compose/to-do/groups")
internal fun Groups(
    resultBackNavigator: ResultBackNavigator<ToDoGroup>,
    argument: GroupsArgument
) {
    val viewModelFactory = GroupsViewModel.createFactory(argument.value)
    val viewModel = viewModel<GroupsViewModel>(factory = viewModelFactory)
    val selectedGroup by viewModel.selectedGroupFlow.collectAsState()

    DisposableEffect(selectedGroup) {
        selectedGroup?.let(resultBackNavigator::setResult)
        onDispose { }
    }

    Groups(viewModel, onBackwardsNavigation = resultBackNavigator::navigateBack)
}
