package com.bekhruz.weatherforecast.data.remote.dto.current_weather_response

import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import com.bekhruz.weatherforecast.presentation.utils.TimeFormat
import com.bekhruz.weatherforecast.presentation.utils.TimeFormattingType

data class CurrentForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)

fun CurrentForecastResponse.asDomain(): CurrentWeatherData =
    CurrentWeatherData(
        tempC = current.temp_c?.toString()?:"",
        name = location.name?:"",
        icon = current.condition?.icon?:"",
        text = current.condition?.text ?: "",
        humidity = String.format("%d%%%nHumidity", current.humidity?:0),
        pressureMb = String.format(
            "%.1f mbar%nPressure", current.pressure_mb?:0.0),
        lastUpdateDate = TimeFormat.getTime(
            current.last_updated_epoch?.toLong() ?: 0L,
            TimeFormattingType.dateWithWeekday
        ),
        windKph = String.format("%.1f km/h%nWind", current.wind_kph?:0.0),
        dailyChanceOfRain = String.format(
            "%d%%%nChance of rain",
            forecast.forecastday?.get(0)?.day?.daily_chance_of_rain?:0
        ),
        lat = location.lat?:0.0,
        lon = location.lon?:0.0,
        hourlyData = forecast.forecastday?.get(0)?.hour?.toHourly()?: listOf()
    )
