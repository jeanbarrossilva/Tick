package com.jeanbarrossilva.tick.feature.todos

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.loadable.contentOrNull
import com.jeanbarrossilva.loadable.list.serialize
import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.todos.ui.NoToDos
import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDoGroupDefaults
import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDoGroups
import com.jeanbarrossilva.tick.feature.todos.ui.ongoing.OngoingCard
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.change.OnBottomAreaAvailabilityChangeListener
import com.jeanbarrossilva.tick.platform.theme.extensions.plus
import com.jeanbarrossilva.tick.std.loadable.ListLoadable
import com.jeanbarrossilva.tick.std.loadable.find
import com.jeanbarrossilva.tick.std.loadable.mapNotNull

@Composable
fun ToDos(
    viewModel: ToDosViewModel,
    onNavigationToFork: () -> Unit,
    onBottomAreaAvailabilityChangeListener: OnBottomAreaAvailabilityChangeListener,
    modifier: Modifier = Modifier
) {
    val groupsLoadable by viewModel.groupsListLoadableFlow.collectAsState()

    ToDos(
        groupsLoadable,
        onToDoToggle = { toDo, isDone -> viewModel.toggle(toDo.id, isDone) },
        onNavigationToFork,
        onBottomAreaAvailabilityChangeListener,
        modifier
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ToDos(
    groupsListLoadable: ListLoadable<ToDoGroup>,
    onToDoToggle: (toDo: ToDo, isDone: Boolean) -> Unit,
    onNavigationToComposer: () -> Unit,
    onBottomAreaAvailabilityChangeListener: OnBottomAreaAvailabilityChangeListener,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val isBottomAreaAvailable by remember(lazyListState) {
        derivedStateOf {
            lazyListState.canScrollForward
        }
    }
    val spacing = ToDoGroupDefaults.spacing
    val topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val ongoingToDoLoadable = remember(groupsListLoadable) {
        groupsListLoadable.mapNotNull { group -> group.toDos().find(ToDo::isDue) }.find(ToDo::isDue)
    }

    DisposableEffect(isBottomAreaAvailable) {
        onBottomAreaAvailabilityChangeListener
            .onBottomAreaAvailabilityChange(isBottomAreaAvailable)
        onDispose { }
    }

    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("To-dos") },
                colors = topAppBarColors,
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigationToComposer) {
                Icon(TickTheme.Icons.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        LazyColumn(
            Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            state = lazyListState,
            contentPadding = padding + PaddingValues(top = spacing) + TickTheme.overlays.fab,
            verticalArrangement = Arrangement.spacedBy(TickTheme.spacings.extraLarge * 2)
        ) {
            item {
                OngoingCard(
                    ongoingToDoLoadable,
                    onDoneToggle = { isDone ->
                        ongoingToDoLoadable.contentOrNull?.let { ongoingToDo ->
                            onToDoToggle(ongoingToDo, isDone)
                        }
                    },
                    Modifier.padding(horizontal = spacing)
                )
            }

            item {
                when (groupsListLoadable) {
                    is ListLoadable.Loading -> ToDoGroups()
                    is ListLoadable.Empty -> NoToDos()
                    is ListLoadable.Populated -> ToDoGroups(groupsListLoadable, onToDoToggle)
                    else -> { }
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingToDosPreview() {
    TickTheme {
        ToDos(groupsListLoadable = ListLoadable.Loading())
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EmptyToDosPreview() {
    TickTheme {
        ToDos(groupsListLoadable = ListLoadable.Empty())
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedToDosPreview() {
    TickTheme {
        ToDos(groupsListLoadable = ListLoadable.Populated(ToDoGroup.samples.serialize()))
    }
}

@Composable
private fun ToDos(
    groupsListLoadable: ListLoadable<ToDoGroup>,
    modifier: Modifier = Modifier
) {
    ToDos(
        groupsListLoadable,
        onToDoToggle = { _, _ -> },
        onNavigationToComposer = { },
        OnBottomAreaAvailabilityChangeListener.empty,
        modifier
    )
}
