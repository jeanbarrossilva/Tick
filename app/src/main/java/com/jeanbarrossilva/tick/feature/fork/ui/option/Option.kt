package com.jeanbarrossilva.tick.feature.fork.ui.option

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.plus

internal object OptionDefaults {
    val shape
        @Composable get() = TickTheme.shapes.large
}

@Composable
internal fun Option(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = OptionDefaults.shape,
    content: @Composable () -> Unit
) {
    FilledTonalButton(
        onClick,
        modifier.fillMaxWidth(),
        shape = shape,
        contentPadding = ButtonDefaults.ContentPadding + PaddingValues(
            vertical = TickTheme.spacings.medium
        )
    ) {
        content()
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun OptionPreview() {
    TickTheme {
        Option(onClick = { }) {
            Text("Option")
        }
    }
}
