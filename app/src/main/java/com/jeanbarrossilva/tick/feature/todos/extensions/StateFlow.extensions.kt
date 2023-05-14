package com.jeanbarrossilva.tick.feature.todos.extensions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/** Converts this [StateFlow] into a [MutableStateFlow]. **/
internal fun <T> StateFlow<T>.toMutableStateFlow(): MutableStateFlow<T> {
    return MutableStateFlow(value)
}
