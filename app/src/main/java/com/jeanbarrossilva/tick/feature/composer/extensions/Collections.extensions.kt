package com.jeanbarrossilva.tick.feature.composer.extensions

import com.jeanbarrossilva.tick.feature.composer.selectable.Selectable
import com.jeanbarrossilva.tick.feature.composer.selectable.SelectableList
import java.io.Serializable

/**
 * Converts this [Collection] into a [SelectableList] and selects its first element.
 **/
internal fun <T : Serializable> Collection<T>.selectFirst(): SelectableList<T> {
    return select {
        indexOf(it) == 0
    }
}

/**
 * Converts this [Collection] into a [SelectableList].
 *
 * @param selection Predicate that determines whether the element that's been given to it is
 * selected.
 **/
private fun <T : Serializable> Collection<T>.select(selection: (T) -> Boolean = { false }):
    SelectableList<T> {
    val elements = map { Selectable(it, selection(it)) }
    return SelectableList(elements)
}
