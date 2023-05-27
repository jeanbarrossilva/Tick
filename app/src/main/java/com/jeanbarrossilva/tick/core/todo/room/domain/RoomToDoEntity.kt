package com.jeanbarrossilva.tick.core.todo.room.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeanbarrossilva.tick.core.domain.ToDo
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "to_dos")
internal data class RoomToDoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "group_id") val groupID: String,
    val title: String,
    @ColumnInfo(name = "due_date_time") val dueDateTime: String?,
    @ColumnInfo(name = "is_done") val isDone: Boolean
) {
    fun toToDo(): ToDo {
        val id = UUID.fromString(id)
        val dueDateTime = dueDateTime?.let(LocalDateTime::parse)
        return ToDo(id, title, dueDateTime, isDone)
    }
}
