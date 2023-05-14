package com.jeanbarrossilva.tick.feature.composer

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.tick.feature.composer.extensions.backwardsNavigationArrow
import com.jeanbarrossilva.tick.feature.composer.ui.reminder.ReminderSetting
import com.jeanbarrossilva.tick.platform.setting.Setting
import com.jeanbarrossilva.tick.platform.setting.extensions.forwardsNavigationArrow
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.plus
import java.time.LocalDateTime

const val COMPOSER_ROUTE = "composer"

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun Composer(
    description: ComposerDescription,
    onBackwardsNavigation: () -> Unit,
    onTitleChange: (title: String) -> Unit,
    onDueDateTimeChange: (dueDate: LocalDateTime?) -> Unit,
    onNavigationToGroups: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add to-do") },
                navigationIcon = {
                    IconButton(onClick = onBackwardsNavigation) {
                        Icon(TickTheme.Icons.backwardsNavigationArrow, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = description.title.isNotBlank(),
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                FloatingActionButton(onClick = onDone) {
                    Icon(TickTheme.Icons.Done, contentDescription = "Done")
                }
            }
        }
    ) { padding ->
        Surface(Modifier.fillMaxSize(), color = TickTheme.colorScheme.background) {
            LazyColumn(
                contentPadding = padding +
                    PaddingValues(TickTheme.sizes.large) +
                    PaddingValues(bottom = 73.dp),
                verticalArrangement = Arrangement.spacedBy(TickTheme.sizes.medium)
            ) {
                item {
                    TextField(
                        description.title,
                        onTitleChange,
                        Modifier.fillMaxWidth(),
                        label = { Text("Title") }
                    )
                }

                item {
                    ReminderSetting(description.dueDateTime, onDueDateTimeChange)
                }

                item {
                    Setting(
                        text = { Text(description.groups.selected) },
                        action = {
                            Icon(
                                TickTheme.Icons.forwardsNavigationArrow,
                                contentDescription = "Change group"
                            )
                        },
                        onClick = onNavigationToGroups,
                        label = { Text("Group") }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ComposerPreview() {
    TickTheme {
        Composer(
            ComposerDescription.sample,
            onBackwardsNavigation = { },
            onTitleChange = { },
            onDueDateTimeChange = { },
            onNavigationToGroups = { },
            onDone = { }
        )
    }
}
