package com.jeanbarrossilva.tick.feature.todos.extensions

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.list.ListLoadable
import com.jeanbarrossilva.loadable.list.SerializableList
import com.jeanbarrossilva.loadable.list.asListLoadable
import java.io.Serializable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Maps each emission to a [ListLoadable] and emits an initial [loading][ListLoadable.Loading]
 * value.
 *
 * @param coroutineScope [CoroutineScope] in which the [StateFlow] will be started and its
 * [value][StateFlow.value] will be shared.
 * @param sharingStarted Strategy for controlling when sharing starts and ends.
 **/
internal fun <T : Serializable?> Flow<SerializableList<T>>.listLoadable(
    coroutineScope: CoroutineScope,
    sharingStarted: SharingStarted
): StateFlow<ListLoadable<T>> {
    return loadable().map(Loadable<SerializableList<T>>::asListLoadable).stateIn(
        coroutineScope,
        sharingStarted,
        initialValue = ListLoadable.Loading()
    )
}
