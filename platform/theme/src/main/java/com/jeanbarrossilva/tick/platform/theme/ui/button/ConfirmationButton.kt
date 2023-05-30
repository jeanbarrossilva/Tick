package com.jeanbarrossilva.tick.platform.theme.ui.button

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme

/**
 * [TextButton] for confirming the performance of an action requested by the user or their
 * understanding about previously shown information.
 *
 * @param onClick Lambda to be run whenever a click occurs.
 * @param modifier [Modifier] to be applied to the underlying [TextButton].
 **/
@Composable
fun ConfirmationButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(onClick, modifier) {
        Text(stringResource(android.R.string.ok))
    }
}

/** Preview of a [ConfirmationButton]. **/
@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ConfirmationButtonPreview() {
    TickTheme {
        Surface(color = TickTheme.colorScheme.background) {
            ConfirmationButton(onClick = { })
        }
    }
}
