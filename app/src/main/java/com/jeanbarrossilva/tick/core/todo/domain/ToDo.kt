package com.jeanbarrossilva.tick.core.todo.domain

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

data class ToDo(
    val id: UUID,
    val title: String,
    val dueDateTime: LocalDateTime?,
    val isDone: Boolean
) : Comparable<ToDo>, Serializable {
    val isDue
        get() = isDue(LocalDateTime.now())

    override fun compareTo(other: ToDo): Int {
        return if (dueDateTime != null && other.dueDateTime == null) {
            1
        } else {
            dueDateTime?.compareTo(other.dueDateTime) ?: title.compareTo(other.title)
        }
    }

    fun isDue(localDateTime: LocalDateTime): Boolean {
        return dueDateTime != null && dueDateTime < localDateTime
    }

    companion object {
        val sample = ToDo(
            UUID.randomUUID(),
            title = "Travel to Italy",
            dueDateTime = LocalDateTime.now(),
            isDone = false
        )
    }
}

/**
 * Gets the [ToDo] whose [ID][ToDo.id] equals to the given one.
 *
 * @param id [ID][ToDo.id] of the [ToDo] to be obtained.
 **/
@Suppress("KDocUnresolvedReference")
internal operator fun Collection<ToDo>.get(id: UUID): ToDo? {
    return find { toDo ->
        toDo.id == id
    }
}
