package com.jeanbarrossilva.tick.platform.theme.sizes

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Sizes for spacing, such as the distance between one component and another or padding.
 *
 * @param extraLarge Greatest possible size.
 * @param large Smaller than [extraLarge], greater than [medium].
 * @param medium Smaller than [large], greater than [small].
 * @param small Smaller than [medium], greater than [extraSmall].
 * @param extraSmall Smallest possible size.
 **/
data class Sizes internal constructor(
    val extraLarge: Dp,
    val large: Dp,
    val medium: Dp,
    val small: Dp,
    val extraSmall: Dp
) {
    companion object {
        /** [Sizes] with [Dp.Unspecified] values. **/
        internal val Unspecified = Sizes(
            extraLarge = Dp.Unspecified,
            large = Dp.Unspecified,
            medium = Dp.Unspecified,
            small = Dp.Unspecified,
            extraSmall = Dp.Unspecified
        )

        /** [Sizes] that's provided by default. **/
        val default
            @Composable get() = Sizes(
                extraLarge = 32.dp,
                large = 24.dp,
                medium = 16.dp,
                small = 8.dp,
                extraSmall = 4.dp
            )
    }
}
