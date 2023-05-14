package com.jeanbarrossilva.tick.feature.composer.selectable

import java.io.Serializable

internal data class Selectable<T : Serializable>(val value: T, val isSelected: Boolean) :
    Serializable
