package com.bekhruz.weatherforecast.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeFormat {
    fun getTime(epochSecond: Long, type:TimeFormattingType):String{
        val givenTime = Date(epochSecond * 1000)
        val timeFormat = when (type) {
            TimeFormattingType.dateWithWeekdayLong -> SimpleDateFormat("EEEE | MMMM d", Locale.getDefault())
            TimeFormattingType.time -> SimpleDateFormat("HH:mm", Locale.getDefault())
            TimeFormattingType.date -> SimpleDateFormat("MMMM d", Locale.getDefault())
            TimeFormattingType.dateWithWeekdayShort -> SimpleDateFormat("EE | MMMM d", Locale.getDefault())
        }
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")
        return timeFormat.format(givenTime)
    }

}

enum class TimeFormattingType {
    date, time, dateWithWeekdayShort, dateWithWeekdayLong
}