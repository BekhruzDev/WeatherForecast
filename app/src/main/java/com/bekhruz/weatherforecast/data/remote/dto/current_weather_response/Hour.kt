package com.bekhruz.weatherforecast.data.remote.dto.current_weather_response

import com.bekhruz.weatherforecast.domain.models.currentweather.Hourly
import com.bekhruz.weatherforecast.presentation.utils.TimeFormat
import com.bekhruz.weatherforecast.presentation.utils.TimeFormattingType

data class Hour(
    val chance_of_rain: Int?,
    val chance_of_snow: Int?,
    val cloud: Int?,
    val condition: ConditionXX?,
    val dewpoint_c: Double?,
    val dewpoint_f: Double?,
    val feelslike_c: Double?,
    val feelslike_f: Double?,
    val gust_kph: Double?,
    val gust_mph: Double?,
    val heatindex_c: Double?,
    val heatindex_f: Double?,
    val humidity: Int?,
    val is_day: Int?,
    val precip_in: Double?,
    val precip_mm: Double?,
    val pressure_in: Double?,
    val pressure_mb: Double?,
    val temp_c: Double?,
    val temp_f: Double?,
    val time: String?,
    val time_epoch: Int?,
    val uv: Double?,
    val vis_km: Double?,
    val vis_miles: Double?,
    val will_it_rain: Int?,
    val will_it_snow: Int?,
    val wind_degree: Int?,
    val wind_dir: String?,
    val wind_kph: Double?,
    val wind_mph: Double?,
    val windchill_c: Double?,
    val windchill_f: Double?
)

fun Hour.toHourly(): Hourly{
    return Hourly(
        time = TimeFormat.getTime(time_epoch?.toLong()?:0L, TimeFormattingType.time),
        icon = condition?.icon?:"",
        tempC = temp_c?.toString()?:"",
        chanceOfRain = String.format("%d%% Rain", chance_of_rain?:0)
    )
}

fun List<Hour>.toHourly(): List<Hourly> {
    return map {
        it.toHourly()
    }
}
