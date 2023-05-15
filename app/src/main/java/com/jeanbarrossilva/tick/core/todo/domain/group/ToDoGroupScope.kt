package com.jeanbarrossilva.tick.core.todo.domain.group

import com.jeanbarrossilva.tick.core.todo.domain.ToDoScope
import com.jeanbarrossilva.tick.core.todo.domain.get
import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import java.util.UUID
import kotlinx.coroutines.flow.first

abstract class ToDoGroupScope {
    protected abstract val repository: ToDoGroupRepository
    protected abstract val groupID: UUID

    suspend fun onToDo(id: UUID): ToDoScope {
        val toDo = repository.fetch().first().getOrThrow(groupID).toDos[id]
        assert(toDo != null) { "To-do $id not found in group $groupID." }
        return getToDoScope(id)
    }

    protected abstract fun getToDoScope(id: UUID): ToDoScope
}
