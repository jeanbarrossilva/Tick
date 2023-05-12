package com.jeanbarrossilva.tick.platform.theme.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection

/** End padding calculated through the [LocalLayoutDirection]. **/
internal val PaddingValues.end
    @Composable get() = calculateEndPadding(LocalLayoutDirection.current)

/** Start padding calculated through the [LocalLayoutDirection]. **/
internal val PaddingValues.start
    @Composable get() = calculateStartPadding(LocalLayoutDirection.current)
