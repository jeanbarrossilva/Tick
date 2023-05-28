package com.jeanbarrossilva.tick.platform.theme.ui.option.group

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.tick.platform.theme.ui.option.Option

/** Scope of an [OptionGroup], through which [option]s can be added. **/
class OptionGroupScope internal constructor() {
    /** [SnapshotStateList] of [OptionMetadata] from which the [Option]s will be displayed. **/
    internal val metadata = mutableStateListOf<OptionMetadata>()

    /**
     * Creates metadata based on the given parameters for the equivalent [Option] to be displayed.
     *
     * @param id [String] that uniquely identifies the metadata.
     * @param icon [Icon] to be shown by the [Option].
     * @param label Label to be shown by the [Option].
     * @param onClick Callback to be run when the [Option] is clicked.
     * @param modifier [Modifier] to applied to the [Option].
     **/
    @JvmName("optionWithRequiredIcon")
    fun option(
        id: String,
        icon: @Composable () -> Unit,
        label: @Composable () -> Unit,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        option(id, icon as (@Composable () -> Unit)?, label, onClick, modifier)
    }

    /**
     * Creates metadata based on the given parameters for the equivalent [Option] to be displayed.
     *
     * @param id [String] that uniquely identifies the metadata.
     * @param onClick Callback to be run when the [Option] is clicked.
     * @param modifier [Modifier] to applied to the [Option].
     * @param label Label to be shown by the [Option].
     **/
    fun option(
        id: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        label: @Composable () -> Unit
    ) {
        option(id, icon = null, label, onClick, modifier)
    }

    /**
     * Creates metadata based on the given parameters for the equivalent [Option] to be displayed.
     *
     * @param id [String] that uniquely identifies the metadata.
     * @param icon [Icon] to be shown by the [Option].
     * @param label Label to be shown by the [Option].
     * @param onClick Callback to be run when the [Option] is clicked.
     * @param modifier [Modifier] to applied to the [Option].
     **/
    private fun option(
        id: String,
        icon: (@Composable () -> Unit)?,
        label: @Composable () -> Unit,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        val metadata = OptionMetadata(id, modifier, icon, label, onClick)
        val index = this.metadata.indexOf(metadata)
        val isExisting = index >= 0
        if (isExisting) replaceMetadataAt(index, metadata) else this.metadata.add(metadata)
    }

    /**
     * Replaces the [OptionMetadata] at the given [index] by [replacement].
     *
     * @param index Index of the [OptionMetadata] to be replaced by [replacement].
     * @param replacement [OptionMetadata] to replace the one at [index] by.
     **/
    private fun replaceMetadataAt(index: Int, replacement: OptionMetadata) {
        metadata.removeAt(index)
        metadata.add(index, replacement)
    }
}
