package com.jeanbarrossilva.tick.feature.todos.ui.group

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

internal data class ToDo(
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
