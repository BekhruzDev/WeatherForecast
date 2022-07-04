package com.bekhruz.weatherforecast.data.remote.dto.currentweather

data class Current(
    val cloud: Int?,
    val condition: Condition?,
    val feelslike_c: Double?,
    val feelslike_f: Double?,
    val gust_kph: Double?,
    val gust_mph: Double?,
    val humidity: Int?,
    val is_day: Int?,
    val last_updated: String?,
    val last_updated_epoch: Int?,
    val precip_in: Double?,
    val precip_mm: Double?,
    val pressure_in: Double?,
    val pressure_mb: Double?,
    val temp_c: Double?,
    val temp_f: Double?,
    val uv: Double?,
    val vis_km: Double?,
    val vis_miles: Double?,
    val wind_degree: Int?,
    val wind_dir: String?,
    val wind_kph: Double?,
    val wind_mph: Double?
)

fun Current.asDomain(): com.bekhruz.weatherforecast.domain.models.currentweather.Current {
    return com.bekhruz.weatherforecast.domain.models.currentweather.Current(
        icon = condition?.icon ?: "",
        text = condition?.text ?: "",
        humidity = humidity ?: 0,
        lastUpdatedEpoch = last_updated_epoch ?: 0,
        pressureMb = pressure_mb ?: 0.0,
        tempC = temp_c ?: 0.0,
        windKph = wind_kph ?: 0.0
    )
}