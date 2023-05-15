package com.jeanbarrossilva.tick.app

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jeanbarrossilva.tick.app.extensions.toDos
import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.composer.COMPOSER_ROUTE
import com.jeanbarrossilva.tick.feature.composer.Composer
import com.jeanbarrossilva.tick.feature.composer.extensions.selectFirst
import com.jeanbarrossilva.tick.feature.settings.SETTINGS_ROUTE
import com.jeanbarrossilva.tick.feature.settings.Settings
import com.jeanbarrossilva.tick.feature.todos.TO_DOS_ROUTE
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
internal fun Tick(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route by remember(backStackEntry) {
        derivedStateOf {
            backStackEntry?.destination?.route
        }
    }
    var isBottomAreaAvailable by remember { mutableStateOf(false) }
    val bottomBarTonalElevation by animateDpAsState(
        if (isBottomAreaAvailable) BottomAppBarDefaults.ContainerElevation else 0.dp
    )

    TickTheme {
        Scaffold(
            modifier,
            bottomBar = {
                BottomAppBar(tonalElevation = bottomBarTonalElevation) {
                    NavigationBarItem(
                        selected = route == TO_DOS_ROUTE,
                        onClick = { navController.navigate(TO_DOS_ROUTE) },
                        icon = { Icon(TickTheme.Icons.CheckCircle, contentDescription = "To-dos") }
                    )

                    NavigationBarItem(
                        selected = route == SETTINGS_ROUTE,
                        onClick = { navController.navigate(SETTINGS_ROUTE) },
                        icon = { Icon(TickTheme.Icons.Settings, contentDescription = "Settings") }
                    )
                }
            },
            containerColor = Color.Transparent,
            contentWindowInsets = ScaffoldDefaults
                .contentWindowInsets
                .only(WindowInsetsSides.Start)
                .only(WindowInsetsSides.End)
                .only(WindowInsetsSides.Bottom)
        ) { padding ->
            NavHost(navController, startDestination = TO_DOS_ROUTE) {
                toDos(
                    navController,
                    onBottomAreaAvailabilityChange = { isBottomAreaAvailable = it },
                    Modifier.padding(padding)
                )

                composable(COMPOSER_ROUTE) {
                    Composer(
                        ToDo.sample.title,
                        ToDo.sample.dueDateTime,
                        ToDoGroup.samples.map(ToDoGroup::title).selectFirst(),
                        onBackwardsNavigation = navController::popBackStack,
                        onTitleChange = { },
                        onDueDateTimeChange = { },
                        onNavigationToGroups = { },
                        onDone = { }
                    )
                }

                composable(SETTINGS_ROUTE) {
                    Settings()
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TickPreview() {
    Tick()
}
