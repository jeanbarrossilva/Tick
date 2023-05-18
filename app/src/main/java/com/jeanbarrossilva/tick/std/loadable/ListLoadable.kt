package com.jeanbarrossilva.tick.std.loadable

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.list.SerializableList
import com.jeanbarrossilva.loadable.list.emptySerializableList
import com.jeanbarrossilva.loadable.map
import java.io.Serializable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

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
    value class Failed<T : Serializable?>(private val error: Throwable) : ListLoadable<T> {
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
 * Returns the result of the given [transform] if this [ListLoadable] is
 * [populated][ListLoadable.Populated]; otherwise, `null`.
 *
 * @param transform Transformation to be made to the [SerializableList].
 **/
fun <I : Serializable?, O> ListLoadable<I>.ifPopulated(transform: SerializableList<I>.() -> O): O? {
    return if (this is ListLoadable.Populated) content.transform() else null
}

/**
 * Converts each emitted [ListLoadable] into a [Loadable] and applies the given [transform] to the
 * underlying [SerializableList].
 *
 * @param transform Transformation to be made to the [SerializableList].
 **/
fun <I : Serializable?, O : Serializable?> Flow<ListLoadable<I>>.innerMap(
    transform: (SerializableList<I>) -> O
): Flow<Loadable<O>> {
    return map { listLoadable ->
        listLoadable.asLoadable().map(transform)
    }
}

/**
 * Maps each emission to a [ListLoadable] and emits an initial [loading][ListLoadable.Loading]
 * value.
 *
 * @param coroutineScope [CoroutineScope] in which the [StateFlow] will be started and its
 * [value][StateFlow.value] will be shared.
 **/
fun <T : Serializable?> Flow<SerializableList<T>>.listLoadable(coroutineScope: CoroutineScope):
    StateFlow<ListLoadable<T>> {
    return loadable().map(Loadable<SerializableList<T>>::asListLoadable).stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        initialValue = ListLoadable.Empty()
    )
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
