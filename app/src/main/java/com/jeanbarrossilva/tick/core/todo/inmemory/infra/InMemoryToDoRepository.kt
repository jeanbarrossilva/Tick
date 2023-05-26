package com.jeanbarrossilva.tick.core.todo.inmemory.infra

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.get
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class InMemoryToDoRepository : ToDoRepository() {
    override val defaultGroupTitle = "Default"

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
