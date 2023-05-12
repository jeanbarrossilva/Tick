package com.jeanbarrossilva.tick.platform.theme.sizes

import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.compositionLocalOf

/** [CompositionLocal] that provides [Sizes]. **/
internal val LocalSizes = compositionLocalOf {
    Sizes.Unspecified
}
