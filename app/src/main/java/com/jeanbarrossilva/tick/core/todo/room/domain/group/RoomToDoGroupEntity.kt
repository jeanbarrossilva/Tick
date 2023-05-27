package com.jeanbarrossilva.tick.core.todo.room.domain.group

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.room.domain.RoomToDoEntity
import java.util.UUID

@Entity(tableName = "groups")
internal data class RoomToDoGroupEntity(@PrimaryKey val id: String, val title: String) {
    fun toToDoGroup(toDoEntities: List<RoomToDoEntity>): ToDoGroup {
        assertRelationship(toDoEntities)
        val id = UUID.fromString(id)
        val toDos = toDoEntities.map(RoomToDoEntity::toToDo)
        return ToDoGroup(id, title, toDos)
    }

    private fun assertRelationship(toDos: List<RoomToDoEntity>) {
        val unrelated = toDos.filterNot { toDo -> toDo.groupID == id }
        val areAllRelated = unrelated.isEmpty()
        require(areAllRelated) {
            "The following to-dos are unrelated to this group ($id):" + unrelated.map { toDo ->
                "\n- $toDo"
            }
        }
    }
}
