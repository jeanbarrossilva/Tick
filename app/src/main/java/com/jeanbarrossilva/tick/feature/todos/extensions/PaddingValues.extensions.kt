package com.jeanbarrossilva.tick.feature.todos.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.tick.platform.theme.extensions.end
import com.jeanbarrossilva.tick.platform.theme.extensions.start

/**
 * Adds the [PaddingValues].
 *
 * @param other [PaddingValues] to add to the receiver one.
 **/
@Composable
internal operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    return PaddingValues(
        start + other.start,
        calculateTopPadding() + other.calculateTopPadding(),
        end + other.end,
        calculateBottomPadding() + other.calculateBottomPadding()
    )
}
