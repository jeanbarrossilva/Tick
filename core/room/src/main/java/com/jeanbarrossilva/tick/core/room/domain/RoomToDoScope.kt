package com.jeanbarrossilva.tick.core.room.domain

import com.jeanbarrossilva.tick.core.domain.ToDoScope

class RoomToDoScope(private val toDoDao: RoomToDoDao, private val id: String) : ToDoScope {
    override suspend fun setDone(isDone: Boolean) {
        toDoDao.setDone(id, isDone)
    }
}
