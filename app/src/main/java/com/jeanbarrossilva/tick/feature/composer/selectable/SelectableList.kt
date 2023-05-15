package com.jeanbarrossilva.tick.feature.composer.selectable

import java.io.Serializable

@JvmInline
internal value class SelectableList<T : Serializable>(
    private val elements: List<Selectable<T>>
) : List<Selectable<T>> by elements, Serializable {
    val selected
        get() = first(Selectable<T>::isSelected).value

    init {
        val selectionCount = elements.count(Selectable<T>::isSelected)
        assert(selectionCount <= 1) { "Cannot select more than one element." }
        assert(selectionCount >= 1) { "Only one element can be selected." }
    }
}
