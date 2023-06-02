package com.jeanbarrossilva.tick.app.ui.scaffold

import android.content.res.Configuration
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.jeanbarrossilva.tick.app.destination.destinations.SettingsDestination
import com.jeanbarrossilva.tick.app.destination.destinations.ToDosDestination
import com.jeanbarrossilva.tick.app.ui.scaffold.icon.SettingsIcon
import com.jeanbarrossilva.tick.app.ui.scaffold.icon.ToDosIcon
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DestinationSpec

@Composable
internal fun NavigationRail(
    navController: NavController,
    destination: DestinationSpec<*>?,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        destination,
        onNavigationToToDos = { navController.navigate(ToDosDestination) },
        onNavigationToSettings = { navController.navigate(SettingsDestination) },
        modifier
    )
}

@Composable
private fun NavigationRail(
    destination: DestinationSpec<*>?,
    onNavigationToToDos: () -> Unit,
    onNavigationToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier) {
        NavigationRailItem(
            selected = destination is ToDosDestination,
            onClick = onNavigationToToDos,
            icon = { ToDosIcon() }
        )

        NavigationRailItem(
            selected = destination is SettingsDestination,
            onClick = onNavigationToSettings,
            icon = { SettingsIcon() }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun NavigationRailPreview() {
    TickTheme {
        NavigationRail(destination = null, onNavigationToToDos = { }, onNavigationToSettings = { })
    }
}
