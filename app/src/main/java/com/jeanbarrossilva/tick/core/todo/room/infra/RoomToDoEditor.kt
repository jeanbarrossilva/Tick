package com.jeanbarrossilva.tick.core.todo.room.infra

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.todo.room.domain.RoomToDoDao
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupDao
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupEntity
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupScope
import java.util.UUID

class RoomToDoEditor(
    private val toDoGroupDao: RoomToDoGroupDao,
    private val toDoDao: RoomToDoDao,
    override val repository: ToDoRepository
) : ToDoEditor() {
    override suspend fun onAddGroup(id: UUID, title: String) {
        val group = RoomToDoGroupEntity("$id", title)
        toDoGroupDao.insert(group)
    }

    override fun getGroupScope(id: UUID): ToDoGroupScope {
        return RoomToDoGroupScope(toDoDao, repository, id)
    }
}
