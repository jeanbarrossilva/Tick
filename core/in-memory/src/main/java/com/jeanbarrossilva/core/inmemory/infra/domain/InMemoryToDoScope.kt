package com.jeanbarrossilva.core.inmemory.infra.domain

import com.jeanbarrossilva.core.inmemory.extensions.replacingBy
import com.jeanbarrossilva.core.inmemory.infra.InMemoryToDoRepository
import com.jeanbarrossilva.tick.core.domain.ToDoScope
import com.jeanbarrossilva.tick.core.domain.group.of
import java.util.UUID

class InMemoryToDoScope(private val repository: InMemoryToDoRepository, private val id: UUID) :
    ToDoScope {
    override suspend fun setDone(isDone: Boolean) {
        val groups = repository.groupsFlow.value
        val (group, toDo) = groups of id
        val toggledToDo = toDo.copy(isDone = isDone)
        val toggledToDos = group.toDos().replacingBy(toggledToDo)
        val toggledGroup = group.copy(toDos = toggledToDos)
        repository.groupsFlow.value = groups.replacingBy(toggledGroup)
    }
}
