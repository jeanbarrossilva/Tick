package com.jeanbarrossilva.tick.core.todo.inmemory.infra.domain.group

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.ToDoScope
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.todo.domain.group.getOrThrow
import com.jeanbarrossilva.tick.core.todo.inmemory.extensions.replacingBy
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.InMemoryToDoRepository
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.domain.InMemoryToDoScope
import java.util.UUID

class InMemoryToDoGroupScope(
    override val repository: InMemoryToDoRepository,
    override val groupID: UUID
) : ToDoGroupScope() {
    override suspend fun onAddToDo(toDo: ToDo) {
        val groups = repository.groupsFlow.value
        val group = groups.getOrThrow(groupID)
        val toggledGroup = group.copy(toDos = group.toDos() + toDo)
        repository.groupsFlow.value = groups.replacingBy(toggledGroup)
    }

    override fun getToDoScope(id: UUID): ToDoScope {
        return InMemoryToDoScope(repository, id)
    }
}
