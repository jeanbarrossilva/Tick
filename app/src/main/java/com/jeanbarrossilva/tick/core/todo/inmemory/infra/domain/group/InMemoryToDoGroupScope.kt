package com.jeanbarrossilva.tick.core.todo.inmemory.infra.domain.group

import com.jeanbarrossilva.tick.core.todo.domain.ToDoScope
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.InMemoryToDoGroupRepository
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.domain.InMemoryToDoScope
import java.util.UUID

class InMemoryToDoGroupScope(
    override val repository: InMemoryToDoGroupRepository,
    override val groupID: UUID
) : ToDoGroupScope() {
    override fun getToDoScope(id: UUID): ToDoScope {
        return InMemoryToDoScope(repository, id)
    }
}
