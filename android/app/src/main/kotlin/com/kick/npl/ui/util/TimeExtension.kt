package com.kick.npl.ui.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDateTime.toPatternedString(pattern: String): String {
    return this.format(
        DateTimeFormatter.ofPattern(pattern)
    )
}

fun LocalDateTime.toUserFriendlyString(): String {
    var formatString = ""
    formatString = when(this.toLocalDate()) {
        LocalDateTime.now().toLocalDate() -> "오늘 a h:mm"
        LocalDateTime.now().toLocalDate().plusDays(1) -> "내일 a h:mm"
        else -> "L월 d일 a h:mm"
    }
    return this.format(DateTimeFormatter.ofPattern(formatString))
}

fun Long.toLocalDateTime(): LocalDateTime {
    val instant = Instant.ofEpochMilli(this)
    val zoneId = ZoneId.systemDefault()
    return LocalDateTime.ofInstant(instant, zoneId)
}

fun LocalDateTime.toMillis(): Long {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Duration.toUserFriendlyString(): String {
    val days = this.toDays()
    val hours = this.toHours() % 24
    val minutes = this.toMinutes() % 60

    val daysString = if (days != 0L) " ${days}일" else ""
    val hoursString = if (hours != 0L) " ${hours}시간" else ""
    val minutesString = if (minutes != 0L) " ${minutes}분" else ""

    return "총${daysString}${hoursString}${minutesString} 이용"
}