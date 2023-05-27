package com.jeanbarrossilva.tick.core.room.domain.group

import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.ToDoScope
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.room.domain.RoomToDoDao
import com.jeanbarrossilva.tick.core.room.domain.RoomToDoEntity
import com.jeanbarrossilva.tick.core.room.domain.RoomToDoScope
import java.util.UUID

class RoomToDoGroupScope(
    private val toDoDao: RoomToDoDao,
    override val repository: ToDoRepository,
    override val groupID: UUID
) : ToDoGroupScope() {
    override suspend fun onAddToDo(toDo: ToDo) {
        val dueDateTimeAsString = toDo.dueDateTime?.toString()
        val entity =
            RoomToDoEntity("${toDo.id}", "$groupID", toDo.title, dueDateTimeAsString, toDo.isDone)
        toDoDao.insert(entity)
    }

    override fun getToDoScope(id: UUID): ToDoScope {
        return RoomToDoScope(toDoDao, "$id")
    }
}
