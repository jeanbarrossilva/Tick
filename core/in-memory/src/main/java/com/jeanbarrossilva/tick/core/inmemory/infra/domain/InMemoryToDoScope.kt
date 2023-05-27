package com.jeanbarrossilva.tick.core.inmemory.infra.domain

import com.jeanbarrossilva.tick.core.domain.ToDoScope
import com.jeanbarrossilva.tick.core.domain.group.of
import com.jeanbarrossilva.tick.core.inmemory.extensions.replacingBy
import com.jeanbarrossilva.tick.core.inmemory.infra.InMemoryToDoRepository
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
