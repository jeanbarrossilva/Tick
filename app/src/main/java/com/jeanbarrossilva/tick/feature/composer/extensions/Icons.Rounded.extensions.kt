package com.jeanbarrossilva.tick.feature.composer.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

/** Icon for backwards navigation that adapts to the direction of the layout. **/
val Icons.Rounded.backwardsNavigationArrow
    @Composable get() = when (LocalLayoutDirection.current) {
        LayoutDirection.Ltr -> KeyboardArrowLeft
        LayoutDirection.Rtl -> KeyboardArrowRight
    }
