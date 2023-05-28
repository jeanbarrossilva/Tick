package com.jeanbarrossilva.tick.feature.composer.todo.ui.reminder

import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.feature.composer.todo.extensions.copy
import com.jeanbarrossilva.tick.feature.composer.todo.extensions.toEpochMilli
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.selectedDate
import com.jeanbarrossilva.tick.platform.theme.extensions.time
import com.jeanbarrossilva.tick.platform.theme.ui.setting.group.SettingGroup
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ReminderSetting(
    dueDateTime: LocalDateTime?,
    onDueDateTimeChange: (dueDateTime: LocalDateTime?) -> Unit,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember(dueDateTime) { dueDateTime != null }
    val switchColor = TickTheme.colorScheme.surfaceTint
    var lastDueDateTime by remember { mutableStateOf(dueDateTime) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = dueDateTime?.toEpochMilli())
    val dateFormatter = remember { DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM) }
    val formattedDate = remember(dueDateTime) { dueDateTime?.format(dateFormatter).orEmpty() }
    var isTimePickerVisible by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(dueDateTime?.hour ?: 0, dueDateTime?.minute ?: 0)
    val timeFormatter = remember { DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT) }
    val formattedTime = remember(dueDateTime) { dueDateTime?.format(timeFormatter).orEmpty() }

    if (isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {
                ConfirmationButton(
                    onClick = {
                        datePickerState.selectedDate?.let {
                            onDueDateTimeChange(dueDateTime?.copy(date = it))
                        }
                        isDatePickerVisible = false
                    }
                )
            }
        ) {
            DatePicker(datePickerState)
        }
    }

    if (isTimePickerVisible) {
        TimePickerDialog(
            onDismissRequest = { isTimePickerVisible = false },
            confirmationButton = {
                ConfirmationButton(
                    onClick = {
                        onDueDateTimeChange(dueDateTime?.copy(time = timePickerState.time))
                        isTimePickerVisible = false
                    }
                )
            }
        ) {
            TimePicker(timePickerState)
        }
    }

    SettingGroup(
        text = { Text("Reminder") },
        action = {
            Switch(
                checked = isExpanded,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        onDueDateTimeChange(lastDueDateTime ?: LocalDateTime.now())
                    } else {
                        lastDueDateTime = dueDateTime
                        onDueDateTimeChange(null)
                    }
                },
                colors = SwitchDefaults
                    .colors(uncheckedThumbColor = switchColor, uncheckedBorderColor = switchColor)
            )
        },
        isExpanded,
        modifier
    ) {
        setting(
            id = "date",
            text = { Text("Date") },
            action = { Text(formattedDate) },
            onClick = { isDatePickerVisible = true }
        )

        setting(
            id = "time",
            text = { Text("Time") },
            action = { Text(formattedTime) },
            onClick = { isTimePickerVisible = true }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DisabledReminderSettingPreview() {
    TickTheme {
        ReminderSetting(dueDateTime = null, onDueDateTimeChange = { })
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EnabledReminderSettingPreview() {
    TickTheme {
        ReminderSetting(dueDateTime = ToDo.sample.dueDateTime, onDueDateTimeChange = { })
    }
}
