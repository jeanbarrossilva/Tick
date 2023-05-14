package com.jeanbarrossilva.tick.feature.todos

import androidx.lifecycle.ViewModel
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.flow.loadableFlow
import com.jeanbarrossilva.loadable.list.serialize
import com.jeanbarrossilva.loadable.map
import com.jeanbarrossilva.tick.feature.todos.extensions.get
import com.jeanbarrossilva.tick.feature.todos.extensions.replacing
import com.jeanbarrossilva.tick.feature.todos.extensions.replacingBy
import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDoGroupDescription
import java.time.LocalDateTime
import java.util.UUID

class ToDosViewModel internal constructor() : ViewModel() {
    internal val descriptionLoadableFlow = loadableFlow<ToDosDescription>().apply {
        val groups = ToDoGroupDescription.samples.serialize()
        val ongoingToDo = groups.flatMap(ToDoGroupDescription::toDos).first { toDo ->
            toDo.dueDateTime >= LocalDateTime.now()
        }
        val description = ToDosDescription(ongoingToDo, groups)
        value = Loadable.Loaded(description)
    }

    internal fun toggle(toDoID: UUID, isDone: Boolean) {
        descriptionLoadableFlow.value = descriptionLoadableFlow.value.map { description ->
            val (toDoGroupDescription, toDo) =
                requireNotNull(description.toDoGroupDescriptions[toDoID])
            val toggledToDos =
                toDoGroupDescription.toDos.replacing(toDoID) { toDo.copy(isDone = isDone) }
            val toggledToDoDescription = toDoGroupDescription.copy(toDos = toggledToDos)
            val toggledToDoGroupDescriptions = description
                .toDoGroupDescriptions
                .replacingBy({ toggledToDoDescription }, toDoGroupDescription::equals)
            description.copy(toDoGroupDescriptions = toggledToDoGroupDescriptions)
        }
    }
}
