package com.jeanbarrossilva.tick.feature.fork.ui.option.group

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.Objects

internal data class OptionMetadata(
    val id: String,
    val modifier: Modifier = Modifier,
    val onClick: () -> Unit,
    val content: @Composable () -> Unit
) {
    override fun equals(other: Any?): Boolean {
        return other is OptionMetadata && id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id, modifier, onClick, content)
    }
}
