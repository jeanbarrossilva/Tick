package com.jeanbarrossilva.tick.app.ui.scaffold

import android.content.res.Configuration
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.tick.app.TickOnBottomAreaAvailabilityChangeListener
import com.jeanbarrossilva.tick.app.destination.destinations.Destination
import com.jeanbarrossilva.tick.app.destination.destinations.SettingsDestination
import com.jeanbarrossilva.tick.app.destination.destinations.ToDosDestination
import com.jeanbarrossilva.tick.app.ui.scaffold.icon.SettingsIcon
import com.jeanbarrossilva.tick.app.ui.scaffold.icon.ToDosIcon
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
internal fun BottomNavigationBar(
    destination: Destination?,
    onNavigationToToDos: () -> Unit,
    onNavigationToSettings: () -> Unit,
    onBottomAreaAvailabilityChangeListener: TickOnBottomAreaAvailabilityChangeListener,
    modifier: Modifier = Modifier
) {
    val tonalElevation = remember(onBottomAreaAvailabilityChangeListener) {
        if (onBottomAreaAvailabilityChangeListener.isAvailable) {
            BottomAppBarDefaults.ContainerElevation
        } else {
            0.dp
        }
    }

    BottomAppBar(modifier, tonalElevation = tonalElevation) {
        NavigationBarItem(
            selected = destination is ToDosDestination,
            onClick = onNavigationToToDos,
            icon = { ToDosIcon() }
        )

        NavigationBarItem(
            selected = destination is SettingsDestination,
            onClick = onNavigationToSettings,
            icon = { SettingsIcon() }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun BottomNavigationBarPreview() {
    TickTheme {
        BottomNavigationBar(
            destination = null,
            onNavigationToToDos = { },
            onNavigationToSettings = { },
            onBottomAreaAvailabilityChangeListener = remember(
                ::TickOnBottomAreaAvailabilityChangeListener
            )
        )
    }
}
