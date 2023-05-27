package com.jeanbarrossilva.core.inmemory.infra

import com.jeanbarrossilva.core.inmemory.infra.domain.group.InMemoryToDoGroupScope
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import java.util.UUID

class InMemoryToDoEditor(override val repository: InMemoryToDoRepository) : ToDoEditor() {
    override suspend fun onAddGroup(id: UUID, title: String) {
        repository.groupsFlow.value = repository.groupsFlow.value + ToDoGroup.empty(id, title)
    }

    override fun getGroupScope(id: UUID): ToDoGroupScope {
        return InMemoryToDoGroupScope(repository, id)
    }
}
