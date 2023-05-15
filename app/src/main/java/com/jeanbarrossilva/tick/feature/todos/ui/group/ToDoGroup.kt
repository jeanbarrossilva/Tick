package com.jeanbarrossilva.tick.feature.todos.ui.group

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.tick.app.R
import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.feature.todos.extensions.Placeholder
import com.jeanbarrossilva.tick.feature.todos.extensions.`if`
import com.jeanbarrossilva.tick.feature.todos.extensions.placeholder
import com.jeanbarrossilva.tick.platform.theme.TickTheme

object ToDoGroupDefaults {
    val spacing
        @Composable get() = TickTheme.sizes.large
}

@Composable
internal fun ToDoGroup(
    description: ToDoGroup,
    onToDoToggle: (ToDo, isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val loadable = remember(description) {
        Loadable.Loaded(description)
    }

    ToDoGroup(loadable, onToDoToggle, modifier)
}

@Composable
internal fun ToDoGroup(
    descriptionLoadable: Loadable<ToDoGroup>,
    onToDoToggle: (ToDo, isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val titleStyle = TickTheme.typography.titleLarge
    val subtitleStyle = TickTheme.typography.titleSmall
    val isPlaceholderVisible =
        remember(descriptionLoadable) { descriptionLoadable !is Loadable.Loaded }
    val headlinePlaceholderColor = TickTheme.colorScheme.outline

    Column(modifier, Arrangement.spacedBy(TickTheme.sizes.extraLarge)) {
        Column(
            Modifier.padding(horizontal = ToDoGroupDefaults.spacing),
            Arrangement.spacedBy(TickTheme.sizes.extraSmall)
        ) {
            Text(
                descriptionLoadable.ifLoaded(ToDoGroup::title).orEmpty(),
                Modifier
                    .placeholder(
                        Placeholder.Text { titleStyle },
                        headlinePlaceholderColor,
                        isPlaceholderVisible
                    )
                    .`if`(isPlaceholderVisible) { fillMaxWidth(.5f) },
                style = titleStyle
            )

            Text(
                pluralStringResource(
                    R.plurals.reminder_count,
                    descriptionLoadable.ifLoaded(ToDoGroup::toDos)?.size ?: 0,
                    descriptionLoadable.ifLoaded(ToDoGroup::toDos)?.size ?: 0
                ),
                Modifier
                    .placeholder(
                        Placeholder.Text { subtitleStyle },
                        headlinePlaceholderColor,
                        isPlaceholderVisible
                    )
                    .`if`(isPlaceholderVisible) { fillMaxWidth(.2f) },
                style = subtitleStyle
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = ToDoGroupDefaults.spacing),
            horizontalArrangement = Arrangement.spacedBy(TickTheme.sizes.large)
        ) {
            if (descriptionLoadable is Loadable.Loaded) {
                items(descriptionLoadable.content.toDos) { toDo ->
                    Card(toDo, onToggle = { isDone -> onToDoToggle(toDo, isDone) })
                }
            } else {
                items(24) {
                    Card(Loadable.Loading(), onToggle = { })
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingToDoGroupPreview() {
    TickTheme {
        Surface(color = TickTheme.colorScheme.background) {
            ToDoGroup(Loadable.Loading(), onToDoToggle = { _, _ -> })
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedToDoGroupPreview() {
    TickTheme {
        Surface(color = TickTheme.colorScheme.background) {
            ToDoGroup(ToDoGroup.sample, onToDoToggle = { _, _ -> })
        }
    }
}
