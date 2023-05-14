package com.jeanbarrossilva.tick.feature.todos

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.ToDoGroup
import java.io.Serializable

internal data class ToDosDescription(
    val ongoingToDo: ToDo,
    val toDoGroups: List<ToDoGroup>
) : Serializable {
    companion object {
        val sample =
            ToDosDescription(ToDo.sample, ToDoGroup.samples)
    }
}
