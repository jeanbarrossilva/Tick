package com.jeanbarrossilva.tick.feature.todos.ui.ongoing

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.contentOrNull
import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.feature.todos.extensions.Placeholder
import com.jeanbarrossilva.tick.feature.todos.extensions.capitalized
import com.jeanbarrossilva.tick.feature.todos.extensions.`if`
import com.jeanbarrossilva.tick.feature.todos.extensions.placeholder
import com.jeanbarrossilva.tick.feature.todos.extensions.relative
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
internal fun OngoingCard(
    toDoLoadable: Loadable<ToDo?>,
    onDoneToggle: (isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val titleStyle = TickTheme.typography.titleMedium
    val dueStyle = TickTheme.typography.labelLarge
    val placeholderColor = TickTheme.colorScheme.surfaceTint
    val isPlaceholderVisible = remember(toDoLoadable) { toDoLoadable !is Loadable.Loaded }
    val spacing = TickTheme.spacings.extraLarge

    if (toDoLoadable !is Loadable.Loaded || toDoLoadable.content != null) {
        Card(modifier.fillMaxWidth(), TickTheme.shapes.extraLarge) {
            Row(
                Modifier.padding(spacing),
                Arrangement.spacedBy(spacing),
                Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = toDoLoadable.contentOrNull?.isDone ?: false,
                    onCheckedChange = onDoneToggle,
                    Modifier
                        .placeholder(
                            Placeholder.height(20.dp),
                            placeholderColor,
                            isPlaceholderVisible
                        )
                        .`if`(isPlaceholderVisible) { width(20.dp) }
                )

                Column(verticalArrangement = Arrangement.spacedBy(TickTheme.spacings.extraSmall)) {
                    Text(
                        toDoLoadable.contentOrNull?.title.orEmpty(),
                        Modifier
                            .placeholder(
                                Placeholder.Text { titleStyle },
                                placeholderColor,
                                isPlaceholderVisible
                            )
                            .`if`(isPlaceholderVisible) { fillMaxWidth(.8f) },
                        style = titleStyle
                    )

                    Text(
                        toDoLoadable.contentOrNull?.dueDateTime?.relative?.capitalized.orEmpty(),
                        Modifier
                            .placeholder(
                                Placeholder.Text { dueStyle },
                                placeholderColor,
                                isPlaceholderVisible
                            )
                            .`if`(isPlaceholderVisible) { fillMaxWidth(.5f) },
                        style = dueStyle
                    )
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingOngoingCardPreview() {
    TickTheme {
        OngoingCard(Loadable.Loading(), onDoneToggle = { })
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedOngoingCardPreview() {
    TickTheme {
        OngoingCard(Loadable.Loaded(ToDo.sample), onDoneToggle = { })
    }
}
