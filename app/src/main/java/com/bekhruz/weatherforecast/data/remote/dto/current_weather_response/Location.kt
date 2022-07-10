package com.bekhruz.weatherforecast.data.remote.dto.current_weather_response


data class Location(
    val country: String?,
    val lat: Double?,
    val localtime: String?,
    val localtime_epoch: Int?,
    val lon: Double?,
    val name: String?,
    val region: String?,
    val tz_id: String?
)