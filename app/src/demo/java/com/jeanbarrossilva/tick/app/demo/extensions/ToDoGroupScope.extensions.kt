package com.jeanbarrossilva.tick.app.demo.extensions

import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroupScope

/**
 * Adds the [toDos].
 *
 * @param toDos [ToDo]s to be added.
 **/
internal suspend fun ToDoGroupScope.addToDos(toDos: List<ToDo>) {
    toDos.forEach { toDo ->
        addToDo(toDo.title, toDo.dueDateTime)
    }
}
