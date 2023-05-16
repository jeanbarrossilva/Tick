package com.jeanbarrossilva.tick.core.todo.inmemory.infra.domain

import com.jeanbarrossilva.tick.core.todo.domain.ToDoScope
import com.jeanbarrossilva.tick.core.todo.domain.group.of
import com.jeanbarrossilva.tick.core.todo.inmemory.extensions.replacingBy
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.InMemoryToDoRepository
import java.util.UUID

class InMemoryToDoScope(
    private val repository: InMemoryToDoRepository,
    private val id: UUID
) : ToDoScope {
    override suspend fun setDone(isDone: Boolean) {
        val groups = repository.groupsFlow.value
        val (group, toDo) = groups of id
        val toggledToDo = toDo.copy(isDone = isDone)
        val toggledToDos = group.toDos().replacingBy(toggledToDo)
        val toggledGroup = group.copy(toDos = toggledToDos)
        repository.groupsFlow.value = groups.replacingBy(toggledGroup)
    }
}
