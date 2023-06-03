package com.jeanbarrossilva.tick.std.loadable

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.list.SerializableList
import com.jeanbarrossilva.loadable.list.emptySerializableList
import com.jeanbarrossilva.loadable.list.serialize
import com.jeanbarrossilva.loadable.map
import java.io.Serializable

/**
 * [Loadable]-like structure for representing different stages of an asynchronously-loaded
 * [SerializableList], as well as its "population" state.
 **/
sealed interface ListLoadable<T : Serializable?> {
    /** Stage in which the [SerializableList] is loading. **/
    class Loading<T : Serializable?> : ListLoadable<T> {
        override fun asLoadable(): Loadable<SerializableList<T>> {
            return Loadable.Loading()
        }
    }

    /** Stage in which the [SerializableList] has been loaded but it's empty. **/
    class Empty<T : Serializable?> : ListLoadable<T> {
        override fun asLoadable(): Loadable<SerializableList<T>> {
            val content = emptySerializableList<T>()
            return Loadable.Loaded(content)
        }
    }

    /**
     * Stage in which the [SerializableList] has been loaded and is populated.
     *
     * @param content The non-empty [SerializableList] itself, containing its elements.
     * @throws IllegalArgumentException If [content] is empty.
     */
    @JvmInline
    value class Populated<T : Serializable?>(val content: SerializableList<T>) : ListLoadable<T> {
        init {
            require(content.isNotEmpty()) {
                "Cannot create a populated ListLoadable with no elements."
            }
        }

        override fun asLoadable(): Loadable<SerializableList<T>> {
            return Loadable.Loaded(content)
        }
    }

    /**
     * Stage in which the [SerializableList] has failed to load and [error] has been thrown.
     *
     * @param error [Throwable] that's been thrown while trying to load the [SerializableList].
     **/
    @JvmInline
    value class Failed<T : Serializable?>(val error: Throwable) : ListLoadable<T> {
        override fun asLoadable(): Loadable<SerializableList<T>> {
            return Loadable.Failed(error)
        }
    }

    /**
     * Since [Loadable] is a sealed interface, it cannot be inherited from. Therefore, this method
     * converts this [ListLoadable] into a [Loadable].
     **/
    fun asLoadable(): Loadable<SerializableList<T>>
}

/**
 * Returns the first element of the [SerializableList] to match the given [predicate], wrapped by a
 * [Loadable] matching the state of this [ListLoadable].
 *
 * @param predicate Condition to which the element to be found should conform.
 **/
fun <T : Serializable?> ListLoadable<T>.find(predicate: (T) -> Boolean): Loadable<T?> {
    return asLoadable().map { content ->
        content.find(predicate)
    }
}

/**
 * Returns the result of the given [transform] if this [ListLoadable] is
 * [populated][ListLoadable.Populated]; otherwise, `null`.
 *
 * @param transform Transformation to be made to the [SerializableList].
 **/
fun <I : Serializable?, O> ListLoadable<I>.ifPopulated(transform: SerializableList<I>.() -> O): O? {
    return if (this is ListLoadable.Populated) content.transform() else null
}

/**
 * Applies [transform] to the [SerializableList]'s elements and returns the ones that aren't
 * `null` if this is [populated][ListLoadable.Populated]; otherwise, creates a new instance with
 * [O] as the type parameter.
 *
 * @param transform Transformation to be made to the elements of the
 * [populated][ListLoadable.Populated] [SerializableList].
 **/
inline fun <I : Serializable?, reified O : Serializable?> ListLoadable<I>.mapNotNull(
    transform: (I) -> O?
): ListLoadable<O> {
    return when (this) {
        is ListLoadable.Loading -> ListLoadable.Loading()
        is ListLoadable.Empty -> ListLoadable.Empty()
        is ListLoadable.Populated -> {
            val mapped = content.mapNotNull(transform).serialize<O>()
            if (mapped.isNotEmpty()) ListLoadable.Populated(mapped) else ListLoadable.Empty()
        }
        is ListLoadable.Failed -> ListLoadable.Failed(error)
    }
}

/** Converts this [Loadable] into a [ListLoadable]. **/
fun <T : Serializable?> Loadable<SerializableList<T>>.asListLoadable(): ListLoadable<T> {
    return when {
        this is Loadable.Loading -> ListLoadable.Loading()
        this is Loadable.Loaded && content.isEmpty() -> ListLoadable.Empty()
        this is Loadable.Loaded && content.isNotEmpty() -> ListLoadable.Populated(content)
        this is Loadable.Failed -> ListLoadable.Failed(error)
        else -> throw IllegalStateException()
    }
}
