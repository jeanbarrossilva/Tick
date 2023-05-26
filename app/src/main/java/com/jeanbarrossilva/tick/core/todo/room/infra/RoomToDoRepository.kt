package com.jeanbarrossilva.tick.core.todo.room.infra

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.todo.room.domain.RoomToDoDao
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupDao
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class RoomToDoRepository(groupDao: RoomToDoGroupDao, private val toDoDao: RoomToDoDao) :
    ToDoRepository() {
    override val defaultGroupTitle = DEFAULT_GROUP_TITLE

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

    companion object {
        internal const val DEFAULT_GROUP_TITLE = "General"
    }
}
