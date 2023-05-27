package com.jeanbarrossilva.tick.core.inmemory.extensions

import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import java.util.UUID

/**
 * Returns a [List] containing exactly the same [ToDo]s of this [Collection], except for the one
 * whose [ID][ToDo.id] is the same as the given one and has been replaced by the result of
 * [replacement].
 *
 * @param id [ID][ToDo.id] of the [ToDo] to be replaced by the result of [replacement].
 * @param replacement Lambda that receives the [ToDo] being currently iterated and returns the
 * replacement of the one whose [ID][ToDo.id] equals to [id].
 * @throws IllegalStateException If multiple [ToDo]s have the same [ID][ToDo.id].
 **/
@Suppress("KDocUnresolvedReference")
internal fun Collection<ToDo>.replacing(id: UUID, replacement: ToDo.() -> ToDo): List<ToDo> {
    return replacingOnceBy(replacement) { toDo ->
        toDo.id == id
    }
}

/**
 * Returns a [List] containing exactly the same [ToDo]s of this [Collection], except for the one
 * whose [ID][ToDo.id] is the same as [replacement]'s.
 *
 * @param replacement [ToDo] to replace the existing one.
 * @throws IllegalStateException If multiple [ToDo]s have the same [ID][ToDo.id].
 **/
internal fun Collection<ToDo>.replacingBy(replacement: ToDo): List<ToDo> {
    return replacing(replacement.id) {
        replacement
    }
}

/**
 * Returns a [List] containing exactly the same [ToDoGroup]s of this [Collection], except for the
 * one whose [ID][ToDoGroup.id] is the same as [replacement]'s.
 *
 * @param replacement [ToDoGroup] to replace the existing one.
 * @throws IllegalStateException If multiple [ToDoGroup]s have the same [ID][ToDoGroup.id].
 **/
internal fun Collection<ToDoGroup>.replacingBy(replacement: ToDoGroup): List<ToDoGroup> {
    return replacingOnceBy({ replacement }) { group ->
        group.id == replacement.id
    }
}

/**
 * Returns a [List] containing exactly the same elements of this [Collection], except for the ones
 * that match the [predicate] and have been replaced by the result of [replacement].
 *
 * @param replacement Lambda that receives the candidate being currently iterated and returns the
 * replacement of the one that matches the [predicate].
 * @param predicate Indicates whether the given candidate should be replaced by the result of
 * [replacement].
 **/

/*
 * Replacing each element through the iterator prevents co-modifications and, therefore, the event
 * of a ConcurrentModificationException being thrown.
 */
private fun <T> Collection<T>.replacingBy(replacement: T.() -> T, predicate: (T) -> Boolean):
    List<T> {
    val iterator = iterator()
    val accumulator = mutableListOf<T>()
    while (iterator.hasNext()) {
        val candidate = iterator.next()
        val isReplaceable = predicate(candidate)
        val accumulation = if (isReplaceable) candidate.replacement() else candidate
        accumulator.add(accumulation)
    }
    return accumulator.toList()
}

/**
 * Returns a [List] containing exactly the same elements of this [Collection], except for the ones
 * that match the [predicate] and have been replaced by the result of [replacement].
 *
 * @param replacement Lambda that receives the candidate being currently iterated and returns the
 * replacement of the one that matches the [predicate].
 * @param predicate Indicates whether the given candidate should be replaced by the result of
 * [replacement].
 * @throws IllegalStateException If multiple elements match the [predicate].
 **/
private fun <T> Collection<T>.replacingOnceBy(replacement: T.() -> T, predicate: (T) -> Boolean):
    List<T> {
    var replaced: T? = null
    return replacingBy(
        replacement
    ) {
        val isMatch = predicate(it)
        if (isMatch && replaced != null) {
            throw IllegalStateException("Multiple predicate matches: $replaced and $it.")
        }
        if (isMatch) {
            replaced = it
        }
        isMatch
    }
}
