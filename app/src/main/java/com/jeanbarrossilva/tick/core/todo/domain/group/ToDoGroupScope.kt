package com.jeanbarrossilva.tick.core.todo.domain.group

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.ToDoScope
import com.jeanbarrossilva.tick.core.todo.domain.get
import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.coroutines.flow.first

abstract class ToDoGroupScope {
    protected abstract val repository: ToDoGroupRepository
    protected abstract val groupID: UUID

    suspend fun addToDo(title: String, dueDateTime: LocalDateTime?) {
        val id = UUID.randomUUID()
        val toDo = ToDo(id, title, dueDateTime, isDone = false)
        onAddToDo(toDo)
    }

    suspend fun onToDo(id: UUID): ToDoScope {
        val toDo = repository.fetch().first().getOrThrow(groupID).toDos[id]
        assert(toDo != null) { "To-do $id not found in group $groupID." }
        return getToDoScope(id)
    }

    protected abstract suspend fun onAddToDo(toDo: ToDo)

    protected abstract fun getToDoScope(id: UUID): ToDoScope
}
