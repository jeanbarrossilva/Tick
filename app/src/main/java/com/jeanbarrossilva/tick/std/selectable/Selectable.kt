package com.jeanbarrossilva.tick.std.selectable

import java.io.Serializable

/**
 * Holds a [value] that can be selected.
 *
 * @param value Value that's selected or unselected, according to [isSelected].
 * @param isSelected Whether [value] is considered to be selected.
 **/
data class Selectable<T : Serializable>(val value: T, val isSelected: Boolean) : Serializable
