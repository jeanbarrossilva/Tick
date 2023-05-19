package com.jeanbarrossilva.tick.core.todo.infra

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class ToDoRepository {
    protected abstract val defaultGroupTitle: String

    fun fetch(): Flow<List<ToDoGroup>> {
        return onFetch().map { groups ->
            (groups + ToDoGroup.empty(defaultGroupTitle)).sorted()
        }
    }

    protected abstract fun onFetch(): Flow<List<ToDoGroup>>
}
