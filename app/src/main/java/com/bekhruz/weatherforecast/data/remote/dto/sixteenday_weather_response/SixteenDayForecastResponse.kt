package com.bekhruz.weatherforecast.data.remote.dto.sixteenday_weather_response

import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDayData
import com.squareup.moshi.Json

data class SixteenDayForecastResponse(
    val city_name: String?,
    val country_code: String?,

    val `data`: List<Data>?,
    val lat: String?,
    val lon: String?,
    val state_code: String?,
    val timezone: String?
)

fun SixteenDayForecastResponse.asDomain(): SixteenDayData {
    return SixteenDayData(dailyForecasts = data?.asDomain() ?: listOf())
}
