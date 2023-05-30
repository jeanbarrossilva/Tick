package com.jeanbarrossilva.tick.feature.settings.ui

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.ui.button.ConfirmationButton

@Composable
internal fun ResetDialog(
    onConfirmation: () -> Unit,
    onDismissal: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissal,
        confirmButton = {
            ConfirmationButton(
                onClick = {
                    onConfirmation()
                    onDismissal()
                }
            )
        },
        modifier,
        dismissButton = { DismissalButton(onClick = onDismissal) },
        title = {
            Text("Are you sure you want to reset the application and clear all of your to-dos?")
        },
        text = { Text("This action is irreversible.") }
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ResetDialogPreview() {
    TickTheme {
        ResetDialog(onConfirmation = { }, onDismissal = { })
    }
}
