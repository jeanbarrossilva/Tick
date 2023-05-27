package com.jeanbarrossilva.tick.core.room.extensions

import com.jeanbarrossilva.core.room.domain.RoomToDoEntity
import com.jeanbarrossilva.tick.core.domain.ToDo
import java.time.LocalDateTime
import java.util.UUID
import org.junit.Assert.assertEquals

/**
 * Asserts that the [entity] is equivalent to the [toDo].
 *
 * @param entity [RoomToDoEntity] to compare to the [toDo].
 * @param toDo [ToDo] to compare to the [entity].
 **/
internal fun assertEquals(entity: RoomToDoEntity, toDo: ToDo) {
    assertEquals(UUID.fromString(entity.id), toDo.id)
    assertEquals(entity.title, toDo.title)
    assertEquals(entity.dueDateTime?.let(LocalDateTime::parse), toDo.dueDateTime)
    assertEquals(entity.isDone, toDo.isDone)
}
