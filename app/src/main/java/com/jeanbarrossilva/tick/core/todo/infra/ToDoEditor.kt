package com.jeanbarrossilva.tick.core.todo.infra

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.todo.domain.group.get
import java.util.UUID
import kotlinx.coroutines.flow.first

abstract class ToDoEditor {
    protected abstract val repository: ToDoRepository

    suspend fun onGroup(id: UUID): ToDoGroupScope {
        val group = repository.fetch().first()[id]
        assert(group != null) { "Group $id not found." }
        return getGroupScope(id)
    }

    protected abstract fun getGroupScope(id: UUID): ToDoGroupScope
}
