package com.jeanbarrossilva.tick.feature.todos

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.tick.feature.todos.extensions.plus
import com.jeanbarrossilva.tick.platform.theme.TickTheme

private val placeholderShape
    @Composable get() = TickTheme.shapes.small

internal const val TO_DOS_ROUTE = "to-dos"

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ToDos(
    onBottomAreaAvailabilityChange: (isAvailable: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val isBottomAreaAvailable by remember(lazyListState) {
        derivedStateOf {
            lazyListState.canScrollForward
        }
    }
    val spacing = TickTheme.sizes.large
    val topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    DisposableEffect(isBottomAreaAvailable) {
        onBottomAreaAvailabilityChange(isBottomAreaAvailable)
        onDispose { }
    }

    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("To-dos") },
                colors = topAppBarColors,
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(TickTheme.Icons.Add, contentDescription = "Add")
            }
        }
    ) {
        LazyColumn(
            Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            state = lazyListState,
            contentPadding = it + PaddingValues(bottom = 73.dp) + PaddingValues(spacing),
            verticalArrangement = Arrangement.spacedBy(TickTheme.sizes.extraLarge * 2)
        ) {
            item {
                OngoingCard()
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(TickTheme.sizes.large * 2)) {
                    repeat(24) {
                        GroupPreview()
                    }
                }
            }
        }
    }
}

@Composable
private fun OngoingCard(modifier: Modifier = Modifier) {
    val spacing = TickTheme.sizes.extraLarge
    val contentColor = TickTheme.colorScheme.surfaceTint

    Card(modifier, TickTheme.shapes.extraLarge) {
        Box(Modifier.padding(spacing)) {
            Column(verticalArrangement = Arrangement.spacedBy(spacing)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .clip(placeholderShape)
                            .background(contentColor)
                            .size(56.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(TickTheme.sizes.small)) {
                        Box(
                            Modifier
                                .clip(placeholderShape)
                                .background(contentColor)
                                .height(24.dp)
                                .fillMaxWidth()
                        )

                        Box(
                            Modifier
                                .clip(placeholderShape)
                                .background(contentColor)
                                .height(24.dp)
                                .fillMaxWidth(.5f)
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(TickTheme.sizes.small)) {
                    Box(
                        Modifier
                            .clip(placeholderShape)
                            .background(contentColor)
                            .height(24.dp)
                            .fillMaxWidth()
                    )

                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        repeat(2) {
                            Box(
                                Modifier
                                    .clip(placeholderShape)
                                    .background(contentColor)
                                    .height(24.dp)
                                    .fillMaxWidth(.2f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupPreview(modifier: Modifier = Modifier) {
    Column(modifier, Arrangement.spacedBy(TickTheme.sizes.large)) {
        Column(verticalArrangement = Arrangement.spacedBy(TickTheme.sizes.small)) {
            Box(
                Modifier
                    .clip(placeholderShape)
                    .background(TickTheme.colorScheme.outline)
                    .height(24.dp)
                    .fillMaxWidth()
            )

            Box(
                Modifier
                    .clip(placeholderShape)
                    .background(TickTheme.colorScheme.outline)
                    .height(24.dp)
                    .fillMaxWidth(.5f)
            )
        }

        Box(
            Modifier
                .clip(placeholderShape)
                .background(TickTheme.colorScheme.outline)
                .aspectRatio(16f / 9f)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ToDosPreview() {
    TickTheme {
        ToDos(onBottomAreaAvailabilityChange = { })
    }
}
