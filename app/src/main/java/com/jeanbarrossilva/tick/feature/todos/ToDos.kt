package com.jeanbarrossilva.tick.feature.todos

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.loadable.list.SerializableList
import com.jeanbarrossilva.loadable.list.serialize
import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.destinations.ComposerDestination
import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDoGroupDefaults
import com.jeanbarrossilva.tick.feature.todos.ui.ongoing.OngoingCard
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.change.OnBottomAreaAvailabilityChangeListener
import com.jeanbarrossilva.tick.platform.theme.extensions.plus
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal const val TO_DOS_ROUTE = "to-dos"

@Composable
@Destination(TO_DOS_ROUTE)
@RootNavGraph(start = true)
fun ToDos(
    navigator: DestinationsNavigator,
    viewModel: ToDosViewModel,
    onBottomAreaAvailabilityChangeListener: OnBottomAreaAvailabilityChangeListener,
    modifier: Modifier = Modifier
) {
    val ongoingToDoLoadable by viewModel.ongoingToDoLoadable.collectAsState()
    val groupsLoadable by viewModel.groupsLoadableFlow.collectAsState()

    ToDos(
        ongoingToDoLoadable,
        groupsLoadable,
        onToDoToggle = { toDo, isDone -> viewModel.toggle(toDo.id, isDone) },
        onNavigationToComposer = { navigator.navigate(ComposerDestination) },
        onBottomAreaAvailabilityChangeListener,
        modifier
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ToDos(
    ongoingToDoLoadable: Loadable<ToDo>,
    groupsLoadable: Loadable<SerializableList<ToDoGroup>>,
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
            contentPadding = padding + PaddingValues(top = spacing) + PaddingValues(bottom = 73.dp),
            verticalArrangement = Arrangement.spacedBy(TickTheme.sizes.extraLarge * 2)
        ) {
            item {
                OngoingCard(
                    ongoingToDoLoadable,
                    onDoneToggle = { isDone ->
                        ongoingToDoLoadable.ifLoaded {
                            onToDoToggle(this, isDone)
                        }
                    },
                    Modifier.padding(horizontal = spacing)
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(TickTheme.sizes.large * 2)) {
                    if (groupsLoadable is Loadable.Loaded) {
                        groupsLoadable.content.forEach {
                            ToDoGroup(it, onToDoToggle)
                        }
                    } else {
                        repeat(24) {
                            ToDoGroup(Loadable.Loading(), onToDoToggle = { _, _ -> })
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedToDosPreview() {
    TickTheme {
        ToDos(
            ongoingToDoLoadable = Loadable.Loaded(ToDo.sample),
            groupsLoadable = Loadable.Loaded(ToDoGroup.samples.serialize()),
            onToDoToggle = { _, _ -> },
            onNavigationToComposer = { },
            onBottomAreaAvailabilityChangeListener = { }
        )
    }
}
