package com.jeanbarrossilva.tick.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.tick.app.R
import com.jeanbarrossilva.tick.feature.settings.Settings
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
internal fun Settings(modifier: Modifier = Modifier) {
    val appName = stringResource(R.string.app_name)
    Settings(appName, modifier)
}
