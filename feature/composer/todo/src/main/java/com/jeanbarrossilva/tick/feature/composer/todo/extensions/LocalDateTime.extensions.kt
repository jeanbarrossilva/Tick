package com.jeanbarrossilva.tick.feature.composer.todo.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

/**
 * Copies the given [LocalDateTime].
 *
 * @param date Defines the [year][LocalDateTime.getYear], the [month][LocalDateTime.getMonth] and
 * the [day-of-month][LocalDateTime.getDayOfMonth].
 * @param time Defines the [hour][LocalDateTime.getHour], the [minute][LocalDateTime.getMinute], the
 * [second][LocalDateTime.getSecond] and the [nano][LocalDateTime.getNano].
 **/
internal fun LocalDateTime.copy(date: LocalDate = toLocalDate(), time: LocalTime = toLocalTime()):
    LocalDateTime {
    return withYear(date.year)
        .withMonth(date.monthValue)
        .withDayOfMonth(date.dayOfMonth)
        .withHour(time.hour)
        .withMinute(time.minute)
        .withSecond(time.second)
        .withNano(time.nano)
}

/**
 * Converts this [LocalDateTime] into to the number of milliseconds from the epoch of
 * 1970-01-01T00:00:00Z.
 **/
internal fun LocalDateTime.toEpochMilli(): Long {
    val zoneId = ZoneId.systemDefault()
    return atZone(zoneId).toInstant().toEpochMilli()
}
