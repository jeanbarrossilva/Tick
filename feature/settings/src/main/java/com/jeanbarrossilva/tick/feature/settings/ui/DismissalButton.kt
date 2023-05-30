package com.jeanbarrossilva.tick.feature.settings.ui

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
internal fun DismissalButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(onClick, modifier) {
        Text(stringResource(android.R.string.cancel))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DismissalButtonPreview() {
    TickTheme {
        Surface(color = TickTheme.colorScheme.background) {
            DismissalButton(onClick = { })
        }
    }
}
