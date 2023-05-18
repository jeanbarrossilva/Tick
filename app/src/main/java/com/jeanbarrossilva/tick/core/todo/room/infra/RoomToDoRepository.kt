package com.jeanbarrossilva.tick.core.todo.room.infra

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.todo.room.domain.RoomToDoDao
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class RoomToDoRepository(groupDao: RoomToDoGroupDao, toDoDao: RoomToDoDao) : ToDoRepository() {
    private val groupsFlow = combine(
        groupDao.selectAll(),
        toDoDao.selectAll()
    ) { groups, toDos ->
        groups.map { group ->
            group.toToDoGroup(toDos.filter { toDo -> toDo.groupID == group.id })
        }
    }

    override fun onFetch(): Flow<List<ToDoGroup>> {
        return groupsFlow
    }
}
