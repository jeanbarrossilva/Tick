package com.jeanbarrossilva.tick.platform.theme.overlay

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Portions of the UI taken by an element that's hierarchically higher.
 *
 * @param fab [PaddingValues] consumed by the FAB.
 **/
data class Overlays internal constructor(val fab: PaddingValues) {
    companion object {
        /** [Overlays] with [Dp.Unspecified] values. **/
        internal val Unspecified = Overlays(fab = PaddingValues(Dp.Unspecified))

        /** [Overlays] that are provided by default. **/
        internal val Default = Overlays(fab = PaddingValues(bottom = 88.dp))
    }
}
