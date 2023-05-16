package com.jeanbarrossilva.tick.core.todo.domain.group

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.get
import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

data class ToDoGroup internal constructor(
    val id: UUID,
    val title: String,
    private val toDos: List<ToDo>
) : Comparable<ToDoGroup>, Serializable {
    override fun compareTo(other: ToDoGroup): Int {
        return if (toDos.isNotEmpty() && other.toDos.isEmpty()) {
            1
        } else if (toDos.isNotEmpty()) {
            toDos.first().compareTo(other.toDos.first())
        } else {
            0
        }
    }

    fun toDos(): List<ToDo> {
        return toDos.sorted()
    }

    companion object {
        val sample = ToDoGroup(
            UUID.randomUUID(),
            title = "Travels",
            toDos = listOf(
                ToDo.sample,
                ToDo(
                    UUID.randomUUID(),
                    title = "Travel to Spain",
                    LocalDateTime.now().plusYears(1),
                    isDone = false
                ),
                ToDo(
                    UUID.randomUUID(),
                    title = "Travel to France",
                    LocalDateTime.now().plusYears(1).minusMonths(1),
                    isDone = false
                )
            )
        )
        val samples = listOf(
            sample,
            ToDoGroup(
                UUID.randomUUID(),
                title = "Housework",
                toDos = listOf(
                    ToDo(
                        UUID.randomUUID(),
                        title = "Clean up the kitchen",
                        LocalDateTime.now().plusHours(1),
                        isDone = true
                    ),
                    ToDo(
                        UUID.randomUUID(),
                        title = "Clean up my room",
                        LocalDateTime.now().plusHours(2),
                        isDone = false
                    ),
                    ToDo(
                        UUID.randomUUID(),
                        title = "Switch mattress",
                        LocalDateTime.now().plusHours(3),
                        isDone = false
                    )
                )
            )
        )
    }
}

/**
 * Gets the [ToDoGroup] that has a [ToDo] whose [ID][ToDo.id] equals to the given one and
 * the found [ToDo] itself.
 *
 * @param toDoID [ID][ToDo.id] of the [ToDo] to be obtained.
 **/
@Suppress("KDocUnresolvedReference", "UNCHECKED_CAST")
infix fun Collection<ToDoGroup>.of(toDoID: UUID): Pair<ToDoGroup, ToDo> {
    if (isEmpty()) {
        throw IllegalArgumentException("Cannot get a to-do from an empty collection.")
    }

    return associate { toDoGroupDescription ->
        toDoGroupDescription to toDoGroupDescription.toDos()[toDoID]
    }
        .filterValues { toDo -> toDo != null }
        .entries
        .first()
        .toPair() as Pair<ToDoGroup, ToDo>
}

/**
 * Gets the [ToDoGroup] whose [ID][ToDoGroup.id] equals to the given one.
 *
 * @param id [ID][ToDoGroup.id] of the [ToDoGroup] to be obtained.
 **/
@Suppress("KDocUnresolvedReference")
internal operator fun Collection<ToDoGroup>.get(id: UUID): ToDoGroup? {
    return find { toDoGroup ->
        toDoGroup.id == id
    }
}

/**
 * Gets the [ToDoGroup] whose [ID][ToDoGroup.id] equals to the given one.
 *
 * @param id [ID][ToDoGroup.id] of the [ToDoGroup] to be obtained.
 * @throws IllegalStateException If a [ToDoGroup] with such [id] isn't found.
 **/
@Suppress("KDocUnresolvedReference")
internal fun Collection<ToDoGroup>.getOrThrow(id: UUID): ToDoGroup {
    return get(id) ?: throw IllegalStateException("Group $id not found.")
}
