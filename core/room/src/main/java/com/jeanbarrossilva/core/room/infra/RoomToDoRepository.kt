package com.jeanbarrossilva.core.room.infra

import com.jeanbarrossilva.core.room.domain.RoomToDoDao
import com.jeanbarrossilva.core.room.domain.group.RoomToDoGroupDao
import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class RoomToDoRepository(groupDao: RoomToDoGroupDao, private val toDoDao: RoomToDoDao) :
    ToDoRepository() {
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

    override fun fetch(toDoID: UUID): Flow<ToDo?> {
        return toDoDao.selectByID("$toDoID").map { entity ->
            entity?.toToDo()
        }
    }
}
