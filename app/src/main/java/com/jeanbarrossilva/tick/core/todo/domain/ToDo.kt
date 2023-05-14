package com.jeanbarrossilva.tick.core.todo.domain

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

data class ToDo internal constructor(
    val id: UUID,
    val title: String,
    val dueDateTime: LocalDateTime,
    val isDone: Boolean
) : Serializable {
    companion object {
        val sample = ToDo(
            UUID.randomUUID(),
            title = "Travel to Italy",
            dueDateTime = LocalDateTime.now(),
            isDone = false
        )
    }
}
