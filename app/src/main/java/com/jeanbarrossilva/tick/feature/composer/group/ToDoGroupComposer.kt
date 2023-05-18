package com.jeanbarrossilva.tick.feature.composer.group

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.composer.todo.extensions.backwardsNavigationArrow
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.plus

@Composable
fun ToDoGroupComposer(
    viewModel: ToDoGroupComposerViewModel,
    onBackwardsNavigation: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title by viewModel.titleFlow.collectAsState()

    ToDoGroupComposer(
        title,
        onTitleChange = viewModel::setTitle,
        onBackwardsNavigation,
        onDone = {
            viewModel.save()
            onDone()
        },
        modifier
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ToDoGroupComposer(
    title: String,
    onTitleChange: (title: String) -> Unit,
    onBackwardsNavigation: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val titleFocusRequester = remember(::FocusRequester)

    LaunchedEffect(Unit) {
        titleFocusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add group") },
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
        Surface(modifier) {
            LazyColumn(
                Modifier.imePadding(),
                contentPadding = padding +
                    PaddingValues(TickTheme.sizes.large) +
                    PaddingValues(bottom = 73.dp)
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
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ToDoGroupComposerPreview() {
    TickTheme {
        ToDoGroupComposer(
            title = ToDoGroup.sample.title,
            onTitleChange = { },
            onBackwardsNavigation = { },
            onDone = { }
        )
    }
}
