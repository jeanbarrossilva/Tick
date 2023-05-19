package com.jeanbarrossilva.tick.feature.todos.ui.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.std.loadable.ListLoadable

private val spacing
    @Composable get() = TickTheme.sizes.large * 2

@Composable
internal fun ToDoGroups(modifier: Modifier = Modifier) {
    Column(modifier, Arrangement.spacedBy(spacing)) {
        repeat(24) {
            ToDoGroup(Loadable.Loading(), onToDoToggle = { _, _ -> })
        }
    }
}

@Composable
internal fun ToDoGroups(
    groupsListLoadable: ListLoadable.Populated<ToDoGroup>,
    onToDoToggle: (ToDo, isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier, Arrangement.spacedBy(spacing)) {
        groupsListLoadable.content.forEach {
            ToDoGroup(it, onToDoToggle)
        }
    }
}
