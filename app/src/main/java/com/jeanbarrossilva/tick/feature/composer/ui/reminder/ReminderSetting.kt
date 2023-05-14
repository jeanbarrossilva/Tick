package com.jeanbarrossilva.tick.feature.composer.ui.reminder

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
import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.feature.composer.extensions.copy
import com.jeanbarrossilva.tick.platform.setting.group.SettingGroup
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ReminderSetting(
    dueDateTime: LocalDateTime?,
    onDueDateTimeChange: (dueDateTime: LocalDateTime?) -> Unit,
    modifier: Modifier = Modifier
) {
    val zoneId = ZoneId.systemDefault()
    val isExpanded = remember(dueDateTime) { dueDateTime != null }
    val switchColor = TickTheme.colorScheme.surfaceTint
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dueDateTime?.atZone(zoneId)?.toInstant()?.toEpochMilli(),
        initialDisplayedMonthMillis = dueDateTime?.atZone(zoneId)?.toInstant()?.toEpochMilli()
    )
    val formattedDate = remember(dueDateTime) {
        dueDateTime?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)).orEmpty()
    }
    var isTimePickerVisible by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        dueDateTime?.hour ?: LocalDateTime.now().hour,
        dueDateTime?.minute ?: LocalDateTime.now().minute
    )
    val formattedTime = remember(dueDateTime) {
        dueDateTime?.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)).orEmpty()
    }

    if (isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {
                ConfirmationButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val selectedInstant = Instant.ofEpochMilli(it)
                            val selectedDate = LocalDateTime.ofInstant(selectedInstant, zoneId)
                            onDueDateTimeChange(
                                dueDateTime?.copy(
                                    year = selectedDate.year,
                                    month = selectedDate.month,
                                    dayOfMonth = selectedDate.dayOfMonth
                                )
                            )
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
                        onDueDateTimeChange(
                            dueDateTime?.copy(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute
                            )
                        )
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
                onCheckedChange = { onDueDateTimeChange(LocalDateTime.now()) },
                colors = SwitchDefaults
                    .colors(uncheckedThumbColor = switchColor, uncheckedBorderColor = switchColor)
            )
        },
        isExpanded,
        modifier
    ) {
        setting(
            text = { Text("Date") },
            action = { Text(formattedDate) },
            onClick = { isDatePickerVisible = true }
        )

        setting(
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
