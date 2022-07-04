package com.bekhruz.weatherforecast.data.remote.dto.currentweather

import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentLocation

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
fun Location.asDomain(): CurrentLocation {
        return CurrentLocation(
            name = name?:"",
            lat = lat?:0.0,
            lon = lon?:0.0
        )
}