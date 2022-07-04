package com.bekhruz.weatherforecast.data.remote.dto.currentweather

import com.bekhruz.weatherforecast.domain.models.currentweather.Weather

data class CurrentForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)

fun CurrentForecast.asDomain(): Weather =
    Weather(
        current = current,
        forecastDay = forecast.forecastday ?: listOf(),
        location = location
    )
