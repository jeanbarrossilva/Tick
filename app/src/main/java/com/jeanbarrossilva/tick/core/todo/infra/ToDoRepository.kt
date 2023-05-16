package com.jeanbarrossilva.tick.core.todo.infra

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun fetch(): Flow<List<ToDoGroup>>
}
