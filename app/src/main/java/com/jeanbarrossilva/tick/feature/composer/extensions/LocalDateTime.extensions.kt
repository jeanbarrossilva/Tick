package com.jeanbarrossilva.tick.feature.composer.extensions

import java.time.LocalDateTime
import java.time.Month

/**
 * Copies the given [LocalDateTime].
 *
 * @param year Value of [LocalDateTime.getYear].
 * @param month Value of [LocalDateTime.getMonth].
 * @param dayOfMonth Value of [LocalDateTime.getDayOfMonth].
 * @param hour Value of [LocalDateTime.getHour].
 * @param minute Value of [LocalDateTime.getMinute].
 **/
internal fun LocalDateTime.copy(
    year: Int = getYear(),
    month: Month = getMonth(),
    dayOfMonth: Int = getDayOfMonth(),
    hour: Int = getHour(),
    minute: Int = getMinute()
): LocalDateTime {
    return LocalDateTime.of(year, month, dayOfMonth, hour, minute)
}
