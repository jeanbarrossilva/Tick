package com.jeanbarrossilva.tick.feature.composer.todo

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
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.composer.todo.extensions.backwardsNavigationArrow
import com.jeanbarrossilva.tick.feature.composer.todo.ui.reminder.ReminderSetting
import com.jeanbarrossilva.tick.platform.setting.Setting
import com.jeanbarrossilva.tick.platform.setting.extensions.forwardsNavigationArrow
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.plus
import com.jeanbarrossilva.tick.std.selectable.SelectableList
import com.jeanbarrossilva.tick.std.selectable.selectFirst
import java.time.LocalDateTime

@Composable
fun ToDoComposer(
    viewModel: ToDoComposerViewModel,
    onBackwardsNavigation: () -> Unit,
    onNavigationToGroups: (groups: SelectableList<ToDoGroup>) -> Unit,
    modifier: Modifier = Modifier
) {
    val title by viewModel.titleFlow.collectAsState()
    val dueDateTime by viewModel.dueDateTimeFlow.collectAsState()
    val groupsLoadable by viewModel.groupsLoadableFlow.collectAsState()

    ToDoComposer(
        title,
        dueDateTime,
        groupsLoadable,
        onBackwardsNavigation,
        onTitleChange = viewModel::setTitle,
        onDueDateTimeChange = viewModel::setDueDateTime,
        onNavigationToGroups,
        onDone = {
            viewModel.save()
            onBackwardsNavigation()
        },
        modifier
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ToDoComposer(
    title: String,
    dueDateTime: LocalDateTime?,
    groupsLoadable: Loadable<SelectableList<ToDoGroup>>,
    onBackwardsNavigation: () -> Unit,
    onTitleChange: (title: String) -> Unit,
    onDueDateTimeChange: (dueDate: LocalDateTime?) -> Unit,
    onNavigationToGroups: (groups: SelectableList<ToDoGroup>) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val titleFocusRequester = remember(::FocusRequester)

    LaunchedEffect(Unit) {
        titleFocusRequester.requestFocus()
    }

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
                visible = title.isNotBlank(),
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                FloatingActionButton(onClick = onDone, Modifier.imePadding()) {
                    Icon(TickTheme.Icons.Done, contentDescription = "Done")
                }
            }
        }
    ) { padding ->
        Surface(Modifier.fillMaxSize(), color = TickTheme.colorScheme.background) {
            LazyColumn(
                Modifier.imePadding(),
                contentPadding = padding +
                    PaddingValues(TickTheme.spacings.large) +
                    TickTheme.overlays.fab,
                verticalArrangement = Arrangement.spacedBy(TickTheme.spacings.medium)
            ) {
                item {
                    TextField(
                        title,
                        onTitleChange,
                        Modifier
                            .focusRequester(titleFocusRequester)
                            .fillMaxWidth(),
                        label = { Text("Title") }
                    )
                }

                item {
                    ReminderSetting(dueDateTime, onDueDateTimeChange)
                }

                item {
                    Setting(
                        text = {
                            Text(
                                groupsLoadable
                                    .ifLoaded(SelectableList<ToDoGroup>::selected)
                                    ?.title
                                    .orEmpty()
                            )
                        },
                        action = {
                            Icon(
                                TickTheme.Icons.forwardsNavigationArrow,
                                contentDescription = "Change group"
                            )
                        },
                        onClick = { groupsLoadable.ifLoaded(onNavigationToGroups) },
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
private fun ToDoComposerPreview() {
    TickTheme {
        ToDoComposer(
            ToDo.sample.title,
            ToDo.sample.dueDateTime,
            Loadable.Loaded(ToDoGroup.samples.selectFirst()),
            onBackwardsNavigation = { },
            onTitleChange = { },
            onDueDateTimeChange = { },
            onNavigationToGroups = { },
            onDone = { }
        )
    }
}
