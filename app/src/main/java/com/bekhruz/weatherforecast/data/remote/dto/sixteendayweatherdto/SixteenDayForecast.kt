package com.bekhruz.weatherforecast.data.remote.dto.sixteendayweatherdto

import com.bekhruz.weatherforecast.data.remote.utils.Mapper
import com.bekhruz.weatherforecast.domain.models.SixteenDay
import com.squareup.moshi.Json

data class SixteenDayForecast(
    val city_name: String?,
    val country_code: String?,
    @Json(name = "`data`")
    val data: List<Data>?,
    val lat: String?,
    val lon: String?,
    val state_code: String?,
    val timezone: String?
)

fun SixteenDayForecast.asDomain(): SixteenDay {
    return SixteenDay(data = data ?: listOf())
}
