package com.jeanbarrossilva.tick.feature.todos.extensions

import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.jeanbarrossilva.tick.platform.theme.TickTheme

abstract class Placeholder protected constructor() {
    @get:Composable
    protected abstract val height: Dp

    internal val modifier = Modifier.`if`({ height.isSpecified }) { height(height) }

    data class Text(
        private val textStyle: @Composable () -> TextStyle = { LocalTextStyle.current }
    ) : Placeholder() {
        override val height
            @Composable get() = with(LocalDensity.current) { textStyle().fontSize.toDp() }
    }

    companion object {
        fun height(height: Dp): Placeholder {
            return object : Placeholder() {
                override val height
                    @Composable get() = height
            }
        }
    }
}

internal fun Modifier.`if`(condition: Boolean, update: @Composable Modifier.() -> Modifier):
    Modifier {
    return `if`({ condition }, update)
}

internal fun Modifier.placeholder(placeholder: Placeholder, color: Color, isVisible: Boolean):
    Modifier {
    return composed {
        placeholder(placeholder, color, TickTheme.shapes.extraSmall, isVisible)
    }
}

internal fun Modifier.placeholder(
    placeholder: Placeholder,
    color: Color,
    shape: Shape,
    isVisible: Boolean
): Modifier {
    return composed {
        placeholder(isVisible, color, shape, PlaceholderHighlight.shimmer()).`if`({ isVisible }) {
            then(placeholder.modifier)
        }
    }
}

private fun Modifier.`if`(
    condition: @Composable Modifier.() -> Boolean,
    update: @Composable Modifier.() -> Modifier
): Modifier {
    @Suppress("UnnecessaryComposedModifier")
    return composed {
        if (condition()) update() else this
    }
}
