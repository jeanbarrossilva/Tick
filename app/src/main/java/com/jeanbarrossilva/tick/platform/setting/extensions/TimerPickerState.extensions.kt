package com.jeanbarrossilva.tick.platform.setting.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.time.LocalTime

/** [LocalTime] that's been set. **/
@OptIn(ExperimentalMaterial3Api::class)
internal val TimePickerState.time
    get() = LocalTime.of(hour, minute)
