package com.bekhruz.weatherforecast.data.remote.dto.currentweather

import com.bekhruz.weatherforecast.domain.models.currentweather.Weather
import com.bekhruz.weatherforecast.domain.models.home.HomeWeatherData
import com.bekhruz.weatherforecast.presentation.utils.TimeFormat
import com.bekhruz.weatherforecast.presentation.utils.TimeFormattingType

data class CurrentForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)

fun CurrentForecast.asDomain(): HomeWeatherData =
    HomeWeatherData(
        temperature = current.temp_c.toString(),
        cityName = location.name.toString(),
        imageLink = current.condition?.icon ?: "",
        lastUpdateDate = TimeFormat.getTime(
            current.last_updated_epoch?.toLong() ?: 0L,
            TimeFormattingType.dateWithWeekday
        )
    )
