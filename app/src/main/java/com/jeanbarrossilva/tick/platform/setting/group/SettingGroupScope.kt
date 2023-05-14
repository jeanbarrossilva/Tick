package com.jeanbarrossilva.tick.platform.setting.group

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.tick.platform.setting.Setting

/** Scope of a [SettingGroup], through which [setting]s can be added. **/
class SettingGroupScope internal constructor() {
    /** [List] of [SettingMetadata] from which the [Setting]s will be displayed. **/
    internal val metadata = mutableListOf<SettingMetadata>()

    /**
     * Creates metadata based on the given parameters for the equivalent [Setting] to be displayed.
     *
     * @param text [Text] to be shown by the [Setting].
     * @param action Action representation to be shown by the [Setting].
     * @param onClick Callback to be run when the [Setting] is clicked.
     * @param modifier [Modifier] to applied to the [Setting].
     **/
    fun setting(
        text: @Composable () -> Unit,
        action: @Composable () -> Unit,
        onClick: (() -> Unit)?,
        modifier: Modifier = Modifier
    ) {
        val metadata = SettingMetadata(modifier, text, action, onClick)
        this.metadata.add(metadata)
    }
}
