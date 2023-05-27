package com.jeanbarrossilva.tick.core.inmemory.infra

import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.get
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class InMemoryToDoRepository : ToDoRepository() {
    internal val groupsFlow = MutableStateFlow(ToDoGroup.samples)

    override fun onFetch(): Flow<List<ToDoGroup>> {
        return groupsFlow
    }

    override fun fetch(toDoID: UUID): Flow<ToDo?> {
        return groupsFlow.map { groups ->
            groups.flatMap(ToDoGroup::toDos)[toDoID]
        }
    }
}
