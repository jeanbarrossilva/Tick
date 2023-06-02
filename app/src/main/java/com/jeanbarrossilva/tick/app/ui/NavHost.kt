package com.jeanbarrossilva.tick.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jeanbarrossilva.tick.app.TickOnBottomAreaAvailabilityChangeListener
import com.jeanbarrossilva.tick.app.destination.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.NavHostEngine

@Composable
internal fun NavHost(
    navController: NavHostController,
    engine: NavHostEngine,
    onBottomAreaAvailabilityChangeListener: TickOnBottomAreaAvailabilityChangeListener,
    modifier: Modifier = Modifier
) {
    DestinationsNavHost(
        NavGraphs.root,
        modifier,
        engine = engine,
        navController = navController,
        dependenciesContainerBuilder = {
            dependency(onBottomAreaAvailabilityChangeListener)
        }
    )
}
