package com.jeanbarrossilva.tick.platform.option.group

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.tick.platform.option.Option
import java.util.Objects

/**
 * Structure that holds the parameters of an [Option].
 *
 * @param id [String] that uniquely identifies the metadata.
 * @param modifier [Modifier] to applied to the [Option].
 * @param icon [Icon] to be shown by the [Option].
 * @param label Label to be shown by the [Option].
 * @param onClick Callback to be run when the [Option] is clicked.
 **/
internal data class OptionMetadata(
    val id: String,
    val modifier: Modifier,
    val icon: (@Composable () -> Unit)?,
    val label: @Composable () -> Unit,
    val onClick: () -> Unit
) {
    override fun equals(other: Any?): Boolean {
        return other is OptionMetadata && id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id, modifier, icon, label, onClick)
    }
}
