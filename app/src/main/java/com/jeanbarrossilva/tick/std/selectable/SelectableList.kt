package com.jeanbarrossilva.tick.std.selectable

import java.io.Serializable

/**
 * [List] capable of holding [Selectable]s.
 *
 * @throws IllegalStateException If none or more than one [Selectable] is selected.
 **/
@JvmInline
value class SelectableList<T : Serializable> internal constructor(
    private val elements: List<Selectable<T>>
) : List<Selectable<T>> by elements, Serializable {
    /** [Selectable] that's currently selected. **/
    val selected
        get() = single(Selectable<T>::isSelected).value

    init {
        val selectionCount = elements.count(Selectable<T>::isSelected)
        assert(selectionCount <= 1) { "Cannot select more than one element." }
        assert(selectionCount >= 1) { "Only one element can be selected." }
    }

    /**
     * Gets the [Selectable] whose [value][Selectable.value] equals to the given one.
     *
     * @param value [Value][Selectable.value] of the [Selectable] to be obtained.
     **/
    @Suppress("KDocUnresolvedReference")
    operator fun get(value: T): Selectable<T>? {
        return find { selectable ->
            selectable.value == value
        }
    }
}

/**
 * Converts this [Collection] into a [SelectableList] and selects the given [element].
 *
 * @param element Element to be selected.
 **/
fun <T : Serializable> Collection<T>.select(element: T): SelectableList<T> {
    return selectIf { _, currentElement ->
        element == currentElement
    }
}

/**
 * Converts this [Collection] into a [SelectableList] and selects its first element.
 **/
fun <T : Serializable> Collection<T>.selectFirst(): SelectableList<T> {
    return selectIf { index, _ ->
        index == 0
    }
}

/**
 * Selects the [Selectable] whose [value][Selectable.value] equals to the given one.
 *
 * @param value [Value][Selectable.value] of the [Selectable] to be selected.
 **/
@Suppress("KDocUnresolvedReference")
fun <T : Serializable> SelectableList<T>.select(value: T): SelectableList<T> {
    return map(Selectable<T>::value).select(value)
}

/**
 * Converts this [Collection] into a [SelectableList].
 *
 * @param selection Predicate that determines whether the element that's been given to it is
 * selected.
 **/
private fun <T : Serializable> Collection<T>.selectIf(selection: (index: Int, T) -> Boolean):
    SelectableList<T> {
    val elements = mapIndexed { index, element -> Selectable(element, selection(index, element)) }
    return SelectableList(elements)
}
