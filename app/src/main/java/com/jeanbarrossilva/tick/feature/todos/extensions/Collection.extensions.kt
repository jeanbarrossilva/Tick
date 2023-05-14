package com.jeanbarrossilva.tick.feature.todos.extensions

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.ToDoGroup
import java.util.UUID

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

/**
 * Gets the [ToDoGroup] that has a [ToDo] whose [ID][ToDo.id] equals to the given one and
 * the found [ToDo] itself.
 *
 * @param toDoID [ID][ToDo.id] of the [ToDo] to be obtained.
 **/
@Suppress("KDocUnresolvedReference")
internal operator fun Collection<ToDoGroup>.get(toDoID: UUID):
    Pair<ToDoGroup, ToDo>? {
    @Suppress("UNCHECKED_CAST")
    return associate { toDoGroupDescription ->
        toDoGroupDescription to toDoGroupDescription.toDos[toDoID]
    }
        .filterValues { toDo -> toDo != null }
        .entries
        .firstOrNull()
        ?.toPair() as Pair<ToDoGroup, ToDo>?
}

/**
 * Returns a [List] containing exactly the same [ToDo]s of this [Collection], except for the one
 * whose [ID][ToDo.id] is the same as the given one and has been replaced by [replacement].
 *
 * @param id [ID][ToDo.id] of the [ToDo] to be replaced by [replacement].
 * @param replacement Lambda that receives the [ToDo] being currently iterated and returns the
 * replacement of the one whose [ID][ToDo.id] equals to [id].
 * @throws IllegalStateException If multiple [ToDo]s have the same [ID][ToDo.id].
 **/
@Suppress("KDocUnresolvedReference")
internal fun Collection<ToDo>.replacing(id: UUID, replacement: ToDo.() -> ToDo): List<ToDo> {
    var replaced: ToDo? = null
    return replacingBy(replacement) { toDo ->
        val isMatch = toDo.id == id
        if (isMatch && replaced != null) {
            throw IllegalStateException("Multiple to-dos with the same ID: $replaced and $toDo.")
        }
        if (isMatch) {
            replaced = toDo
        }
        isMatch
    }
}

/**
 * Returns a [List] containing exactly the same elements of this [Collection], except for the ones
 * that match the [predicate] and have been replaced by [replacement].
 *
 * @param replacement Lambda that receives the element being currently iterated and returns the
 * replacement of the one that matches the [predicate].
 * @param predicate Indicates whether the given element should be replaced by [replacement].
 **/

/*
 * Replacing each element through the iterator prevents co-modifications and, therefore, the event
 * of a ConcurrentModificationException being thrown.
 */
internal fun <T> Collection<T>.replacingBy(replacement: T.() -> T, predicate: (T) -> Boolean):
    List<T> {
    val iterator = iterator()
    val accumulator = mutableListOf<T>()
    while (iterator.hasNext()) {
        val current = iterator.next()
        val isReplaceable = predicate(current)
        val accumulation = if (isReplaceable) current.replacement() else current
        accumulator.add(accumulation)
    }
    return accumulator.toList()
}
