package com.jeanbarrossilva.tick.feature.fork.ui.option.group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier

internal class OptionGroupScope internal constructor() {
    val metadata = mutableStateListOf<OptionMetadata>()

    fun option(
        id: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        val metadata = OptionMetadata(id, modifier, onClick, content)
        val index = this.metadata.indexOf(metadata)
        val isExisting = index >= 0
        if (isExisting) replaceMetadataAt(index, metadata) else this.metadata.add(metadata)
    }

    private fun replaceMetadataAt(index: Int, replacement: OptionMetadata) {
        metadata.removeAt(index)
        metadata.add(index, replacement)
    }
}
