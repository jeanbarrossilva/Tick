package com.jeanbarrossilva.tick.platform.setting.extensions

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/** [LocalDate] that's been selected. **/
@OptIn(ExperimentalMaterial3Api::class)
internal val DatePickerState.selectedDate
    get() = selectedDateMillis?.let {
        val zoneId = ZoneId.systemDefault()
        Instant.ofEpochMilli(it).atZone(zoneId).toLocalDate().plusDays(1)
    }
