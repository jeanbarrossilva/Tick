package com.jeanbarrossilva.tick.platform.theme.ui.option.group

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.bottom
import com.jeanbarrossilva.tick.platform.theme.extensions.top
import com.jeanbarrossilva.tick.platform.theme.ui.option.Option
import com.jeanbarrossilva.tick.platform.theme.ui.option.OptionDefaults

/**
 * [Option]s vertically grouped together.
 *
 * @param modifier [Modifier] to be applied to the underlying [LazyColumn].
 * @param contentPadding [PaddingValues] to be applied to the content.
 * @param content [Option]s to be added through the given [OptionGroupScope].
 **/
@Composable
fun OptionGroup(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    content: OptionGroupScope.() -> Unit
) {
    val scope = remember(::OptionGroupScope)

    LazyColumn(
        modifier,
        contentPadding = contentPadding
    ) {
        scope.content()
        itemsIndexed(scope.metadata) { index, metadata ->
            Option(
                metadata.icon,
                metadata.label,
                metadata.onClick,
                metadata.modifier,
                shape = when (index) {
                    0 -> OptionDefaults.shape.top
                    scope.metadata.lastIndex -> OptionDefaults.shape.bottom
                    else -> RectangleShape
                }
            )

            if (index != scope.metadata.lastIndex) {
                Divider()
            }
        }
    }
}

/** Preview of an [OptionGroup]. **/
@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun OptionGroupPreview() {
    TickTheme {
        OptionGroup {
            repeat(4) { index ->
                option(id = "$index", onClick = { }) {
                    Text("#$index")
                }
            }
        }
    }
}
