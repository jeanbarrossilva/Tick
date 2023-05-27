package com.jeanbarrossilva.tick.core.infra

import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

abstract class ToDoRepository {
    fun fetch(): Flow<List<ToDoGroup>> {
        return onFetch().distinctUntilChanged().map(List<ToDoGroup>::sorted)
    }

    abstract fun fetch(toDoID: UUID): Flow<ToDo?>

    protected abstract fun onFetch(): Flow<List<ToDoGroup>>
}
