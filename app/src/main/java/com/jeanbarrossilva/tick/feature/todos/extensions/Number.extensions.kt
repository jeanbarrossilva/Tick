package com.jeanbarrossilva.tick.feature.todos.extensions

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

/**
 * Converts the given amount of pixels into [Dp].
 *
 * @param density [Density] through which this will be converted into [Dp].
 **/
internal fun <T : Number> T.toDp(density: Density): Dp {
    return with(density) {
        toFloat().toDp()
    }
}
