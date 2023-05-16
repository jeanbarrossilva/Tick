package com.jeanbarrossilva.tick.core.todo.infra

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class ToDoRepository {
    fun fetch(): Flow<List<ToDoGroup>> {
        return onFetch().map(List<ToDoGroup>::sorted)
    }

    protected abstract fun onFetch(): Flow<List<ToDoGroup>>
}
