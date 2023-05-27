package com.jeanbarrossilva.tick.core.inmemory.infra.domain.group

import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.ToDoScope
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.domain.group.getOrThrow
import com.jeanbarrossilva.tick.core.inmemory.extensions.replacingBy
import com.jeanbarrossilva.tick.core.inmemory.infra.InMemoryToDoRepository
import com.jeanbarrossilva.tick.core.inmemory.infra.domain.InMemoryToDoScope
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
