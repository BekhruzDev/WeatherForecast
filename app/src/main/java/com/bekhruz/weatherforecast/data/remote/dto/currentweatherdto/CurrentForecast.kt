package com.bekhruz.weatherforecast.data.remote.dto.currentweatherdto

import com.bekhruz.weatherforecast.data.remote.utils.Mapper
import com.bekhruz.weatherforecast.domain.models.Weather

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
