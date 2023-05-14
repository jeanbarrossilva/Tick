package com.jeanbarrossilva.tick.app.extensions

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeanbarrossilva.tick.feature.composer.COMPOSER_ROUTE
import com.jeanbarrossilva.tick.feature.todos.TO_DOS_ROUTE
import com.jeanbarrossilva.tick.feature.todos.ToDos

/**
 * Adds [ToDos] to the [NavGraphBuilder].
 *
 * @param navController [NavController] to navigate with.
 * @param onBottomAreaAvailabilityChange Callback run whenever the bottom area is free of content.
 * @param modifier [Modifier] to be applied to [ToDos].
 **/
internal fun NavGraphBuilder.toDos(
    navController: NavController,
    onBottomAreaAvailabilityChange: (isAvailable: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(TO_DOS_ROUTE) {
        ToDos(
            viewModel(),
            onNavigationToComposer = { navController.navigate(COMPOSER_ROUTE) },
            onBottomAreaAvailabilityChange,
            modifier
        )
    }
}
