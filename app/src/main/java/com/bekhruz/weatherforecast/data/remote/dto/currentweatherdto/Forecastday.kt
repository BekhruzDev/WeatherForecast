package com.bekhruz.weatherforecast.data.remote.dto.currentweatherdto

import com.bekhruz.weatherforecast.data.remote.utils.ListMapper
import com.bekhruz.weatherforecast.data.remote.utils.Mapper
import com.bekhruz.weatherforecast.domain.models.ForecastDay

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