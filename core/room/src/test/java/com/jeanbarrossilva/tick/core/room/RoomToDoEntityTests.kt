package com.jeanbarrossilva.tick.core.room

import com.jeanbarrossilva.tick.core.room.domain.RoomToDoEntity
import com.jeanbarrossilva.tick.core.room.extensions.assertEquals
import java.time.LocalDateTime
import java.util.UUID
import org.junit.Test

internal class RoomToDoEntityTests {
    @Test
    fun convertsToToDo() {
        val id = UUID.randomUUID()
        val groupID = UUID.randomUUID()
        val title = "A"
        val dueDateTime = LocalDateTime.now()
        val isDone = false
        val entity = RoomToDoEntity("$id", "$groupID", title, "$dueDateTime", isDone)
        val toDo = entity.toToDo()
        assertEquals(entity, toDo)
    }

    @Test
    fun convertsToToDoWithoutDueDateTime() {
        val id = UUID.randomUUID().toString()
        val groupID = UUID.randomUUID().toString()
        val title = "A"
        val isDone = false
        val entity = RoomToDoEntity(id, groupID, title, dueDateTime = null, isDone)
        val toDo = entity.toToDo()
        assertEquals(entity, toDo)
    }
}
