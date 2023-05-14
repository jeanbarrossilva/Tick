package com.jeanbarrossilva.tick.feature.composer.ui.reminder

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmationButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    DatePickerDialog(
        onDismissRequest,
        confirmationButton,
        modifier
    ) {
        Box(
            Modifier.padding(TickTheme.sizes.extraLarge).fillMaxWidth(),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TimePickerDialogPreview() {
    TickTheme {
        TimePickerDialog(
            onDismissRequest = { },
            confirmationButton = {
                ConfirmationButton(onClick = { })
            }
        ) {
            TimePicker(rememberTimePickerState())
        }
    }
}
