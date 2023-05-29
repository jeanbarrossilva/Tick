package com.jeanbarrossilva.tick.feature.todos.extensions

import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup

/** Filters out [ToDoGroup]s without [to-dos][ToDoGroup.toDos]. **/
internal fun Collection<ToDoGroup>.filterNotEmpty(): List<ToDoGroup> {
    return filter { group ->
        group.toDos().isNotEmpty()
    }
}
