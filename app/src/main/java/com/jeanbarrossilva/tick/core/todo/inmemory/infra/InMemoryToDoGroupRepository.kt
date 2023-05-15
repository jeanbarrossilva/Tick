package com.jeanbarrossilva.tick.core.todo.inmemory.infra

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryToDoGroupRepository : ToDoGroupRepository {
    internal val groupsFlow = MutableStateFlow(ToDoGroup.samples)

    override fun fetch(): Flow<List<ToDoGroup>> {
        return groupsFlow
    }
}
