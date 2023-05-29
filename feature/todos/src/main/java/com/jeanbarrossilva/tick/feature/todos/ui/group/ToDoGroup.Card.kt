package com.jeanbarrossilva.tick.feature.todos.ui.group

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.feature.todos.extensions.Placeholder
import com.jeanbarrossilva.tick.feature.todos.extensions.capitalized
import com.jeanbarrossilva.tick.feature.todos.extensions.`if`
import com.jeanbarrossilva.tick.feature.todos.extensions.placeholder
import com.jeanbarrossilva.tick.feature.todos.extensions.relative
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
internal fun Card(toDo: ToDo, onToggle: (isDone: Boolean) -> Unit, modifier: Modifier = Modifier) {
    val loadable = remember(toDo) {
        Loadable.Loaded(toDo)
    }

    Card(loadable, onToggle, modifier)
}

@Composable
internal fun Card(
    toDoLoadable: Loadable<ToDo>,
    onToggle: (isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val titleStyle = TickTheme.typography.titleLarge
    val subtitleStyle = TickTheme.typography.titleSmall
    val isPlaceholderVisible = remember(toDoLoadable) { toDoLoadable !is Loadable.Loaded }
    val placeholderColor = TickTheme.colorScheme.surfaceTint

    Card(modifier.width(256.dp)) {
        Column(
            Modifier
                .padding(TickTheme.spacings.extraLarge * 2)
                .fillMaxWidth(),
            Arrangement.spacedBy(TickTheme.spacings.large, Alignment.CenterVertically),
            Alignment.CenterHorizontally
        ) {
            Checkbox(
                checked = toDoLoadable.ifLoaded(ToDo::isDone) ?: false,
                onCheckedChange = onToggle,
                Modifier
                    .placeholder(Placeholder.height(20.dp), placeholderColor, isPlaceholderVisible)
                    .`if`(isPlaceholderVisible) { width(20.dp) }
            )

            Column(
                Modifier.fillMaxWidth(),
                Arrangement.spacedBy(TickTheme.spacings.medium),
                Alignment.CenterHorizontally
            ) {
                Text(
                    toDoLoadable.ifLoaded(ToDo::title).orEmpty(),
                    Modifier
                        .placeholder(
                            Placeholder.Text { titleStyle },
                            placeholderColor,
                            isPlaceholderVisible
                        )
                        .`if`(isPlaceholderVisible) { fillMaxWidth(.5f) },
                    textAlign = TextAlign.Center,
                    style = titleStyle
                )

                Text(
                    toDoLoadable.ifLoaded(ToDo::dueDateTime)?.relative?.capitalized.orEmpty(),
                    Modifier
                        .placeholder(
                            Placeholder.Text { subtitleStyle },
                            placeholderColor,
                            isPlaceholderVisible
                        )
                        .`if`(isPlaceholderVisible) { fillMaxWidth(.2f) },
                    textAlign = TextAlign.Center,
                    style = subtitleStyle
                )
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingCardPreview() {
    TickTheme {
        Card(Loadable.Loading(), onToggle = { })
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedCardPreview() {
    TickTheme {
        Card(ToDo.sample, onToggle = { })
    }
}
