package com.jeanbarrossilva.tick.core.todo.infra

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class ToDoRepository {
    protected abstract val defaultGroupTitle: String

    fun fetch(): Flow<List<ToDoGroup>> {
        return onFetch().map { groups ->
            (groups + ToDoGroup.empty(defaultGroupTitle)).sorted()
        }
    }

    abstract fun fetch(toDoID: UUID): Flow<ToDo?>

    protected abstract fun onFetch(): Flow<List<ToDoGroup>>
}
