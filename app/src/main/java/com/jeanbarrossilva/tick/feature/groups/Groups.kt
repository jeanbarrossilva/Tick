package com.jeanbarrossilva.tick.feature.groups

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.composer.todo.extensions.backwardsNavigationArrow
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.plus
import com.jeanbarrossilva.tick.std.selectable.SelectableList
import com.jeanbarrossilva.tick.std.selectable.selectFirst

@Composable
fun Groups(
    viewModel: GroupsViewModel,
    onBackwardsNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val groups by viewModel.groupsFlow.collectAsState()
    Groups(groups, onGroupSelection = viewModel::select, onBackwardsNavigation, modifier)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun Groups(
    groups: SelectableList<ToDoGroup>,
    onGroupSelection: (group: ToDoGroup) -> Unit,
    onBackwardsNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val spacing = TickTheme.spacings.medium

    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Groups") },
                navigationIcon = {
                    IconButton(onClick = onBackwardsNavigation) {
                        Icon(TickTheme.Icons.backwardsNavigationArrow, contentDescription = "Back")
                    }
                },
                scrollBehavior = topAppBarScrollBehavior
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .fillMaxWidth(),
            contentPadding = padding +
                PaddingValues(top = TickTheme.spacings.large) +
                TickTheme.overlays.fab
        ) {
            items(groups) { selectable ->
                Row(
                    Modifier.padding(horizontal = spacing),
                    Arrangement.spacedBy(spacing),
                    Alignment.CenterVertically
                ) {
                    RadioButton(
                        selectable.isSelected,
                        onClick = { onGroupSelection(selectable.value) }
                    )

                    Text(selectable.value.title)
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun GroupsPreview() {
    TickTheme {
        Groups(ToDoGroup.samples.selectFirst(), onGroupSelection = { }, onBackwardsNavigation = { })
    }
}
