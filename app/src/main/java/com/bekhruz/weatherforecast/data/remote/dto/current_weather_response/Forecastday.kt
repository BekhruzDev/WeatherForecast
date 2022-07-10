package com.bekhruz.weatherforecast.data.remote.dto.current_weather_response


data class Forecastday(
    val astro: Astro?,
    val date: String?,
    val date_epoch: Int?,
    val day: Day?,
    val hour: List<Hour>?
)
