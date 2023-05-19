package com.jeanbarrossilva.tick.core.todo.inmemory.infra

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryToDoRepository : ToDoRepository() {
    override val defaultGroupTitle = "Default"

    internal val groupsFlow = MutableStateFlow(ToDoGroup.samples)

    override fun onFetch(): Flow<List<ToDoGroup>> {
        return groupsFlow
    }
}
