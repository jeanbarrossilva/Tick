package com.jeanbarrossilva.tick.feature.todos.extensions

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.todos.ToDosDescription
import java.time.LocalDateTime

/** Converts these [ToDoGroup]s into a [ToDosDescription]. **/
internal fun List<ToDoGroup>.toToDosDescription(): ToDosDescription {
    val ongoingToDo =
        flatMap(ToDoGroup::toDos).first { toDo -> toDo.dueDateTime >= LocalDateTime.now() }
    return ToDosDescription(ongoingToDo, this)
}
