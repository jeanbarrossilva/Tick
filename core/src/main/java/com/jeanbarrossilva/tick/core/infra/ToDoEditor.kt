package com.jeanbarrossilva.tick.core.infra

import com.jeanbarrossilva.tick.core.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.domain.group.get
import java.util.UUID
import kotlinx.coroutines.flow.first

abstract class ToDoEditor {
    protected abstract val repository: ToDoRepository

    suspend fun addGroup(title: String): UUID {
        val id = UUID.randomUUID()
        onAddGroup(id, title)
        return id
    }

    suspend fun onGroup(id: UUID): ToDoGroupScope {
        val group = repository.fetch().first()[id]
        assert(group != null) { "Group $id not found." }
        return getGroupScope(id)
    }

    protected abstract suspend fun onAddGroup(id: UUID, title: String)

    protected abstract fun getGroupScope(id: UUID): ToDoGroupScope
}
