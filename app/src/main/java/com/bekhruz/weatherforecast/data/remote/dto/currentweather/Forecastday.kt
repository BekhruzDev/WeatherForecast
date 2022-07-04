package com.bekhruz.weatherforecast.data.remote.dto.currentweather

import com.bekhruz.weatherforecast.domain.models.currentweather.ForecastDay

data class Forecastday(
    val astro: Astro?,
    val date: String?,
    val date_epoch: Int?,
    val day: Day?,
    val hour: List<Hour>?
)

fun Forecastday.asDomain(): ForecastDay {
        return ForecastDay(
            dailyChanceOfRain = day?.daily_chance_of_rain?:0,
            hour = hour?: listOf()
        )
}