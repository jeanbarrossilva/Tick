package com.jeanbarrossilva.tick.platform.theme.overlay

import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.compositionLocalOf

/** [CompositionLocal] that provides [Overlays]. **/
internal val LocalOverlays = compositionLocalOf {
    Overlays.Unspecified
}
