package com.jeanbarrossilva.tick.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeanbarrossilva.tick.feature.settings.Settings
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
internal fun Settings(modifier: Modifier = Modifier) {
    Settings(modifier)
}
