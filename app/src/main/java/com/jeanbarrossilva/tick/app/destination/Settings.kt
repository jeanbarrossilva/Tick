package com.jeanbarrossilva.tick.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.tick.app.R
import com.jeanbarrossilva.tick.app.destination.destinations.ToDosDestination
import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import com.jeanbarrossilva.tick.feature.settings.Settings
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
@Destination
internal fun Settings(navigator: DestinationsNavigator, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val editor = koinInject<ToDoEditor>()
    val coroutineScope = rememberCoroutineScope()
    val appName = stringResource(R.string.app_name)
    val versionName = remember(context) {
        @Suppress("DEPRECATION")
        context.packageManager?.getPackageInfo(context.packageName, 0)?.versionName ?: "?.?.?"
    }

    Settings(
        appName,
        versionName,
        onReset = {
            coroutineScope.launch { editor.clear() }
            navigator.navigate(ToDosDestination)
        },
        modifier
    )
}
