package com.jeanbarrossilva.tick.platform.setting.group

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.tick.platform.setting.Setting

/**
 * Structure that holds the parameters of a [Setting].
 *
 * @param modifier [Modifier] to applied to the [Setting].
 * @param text [Text] to be shown by the [Setting].
 * @param action Action representation to be shown by the [Setting].
 * @param onClick Callback to be run when the [Setting] is clicked.
 **/
internal data class SettingMetadata(
    val modifier: Modifier,
    val text: @Composable () -> Unit,
    val action: @Composable () -> Unit,
    val onClick: (() -> Unit)?
)
