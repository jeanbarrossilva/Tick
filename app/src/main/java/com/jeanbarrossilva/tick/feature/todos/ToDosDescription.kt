package com.jeanbarrossilva.tick.feature.todos

import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDo
import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDoGroupDescription
import java.io.Serializable

internal data class ToDosDescription(
    val ongoingToDo: ToDo,
    val toDoGroupDescriptions: List<ToDoGroupDescription>
) : Serializable {
    companion object {
        val sample =
            ToDosDescription(ToDo.sample, ToDoGroupDescription.samples)
    }
}
