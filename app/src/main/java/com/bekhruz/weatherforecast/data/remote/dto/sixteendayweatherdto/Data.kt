package com.bekhruz.weatherforecast.data.remote.dto.sixteendayweatherdto

import com.bekhruz.weatherforecast.data.remote.utils.ListMapper
import com.bekhruz.weatherforecast.data.remote.utils.Mapper
import com.bekhruz.weatherforecast.domain.models.SixteenDayData

data class Data(
    val app_max_temp: Double?,
    val app_min_temp: Double?,
    val clouds: Int?,
    val clouds_hi: Int?,
    val clouds_low: Int?,
    val clouds_mid: Int?,
    val datetime: String?,
    val dewpt: Double?,
    val high_temp: Double?,
    val low_temp: Double?,
    val max_dhi: Any?,
    val max_temp: Double?,
    val min_temp: Double?,
    val moon_phase: Double?,
    val moon_phase_lunation: Double?,
    val moonrise_ts: Int?,
    val moonset_ts: Int?,
    val ozone: Double?,
    val pop: Int?,
    val precip: Double?,
    val pres: Double?,
    val rh: Int?,
    val slp: Double?,
    val snow: Int?,
    val snow_depth: Int?,
    val sunrise_ts: Int?,
    val sunset_ts: Int?,
    val temp: Double?,
    val ts: Int?,
    val uv: Double?,
    val valid_date: String?,
    val vis: Double?,
    val weather: Weather?,
    val wind_cdir: String?,
    val wind_cdir_full: String?,
    val wind_dir: Int?,
    val wind_gust_spd: Double?,
    val wind_spd: Double?
)

fun Data.asDomain(): SixteenDayData {
    return SixteenDayData(
        maxTemp = max_temp ?: 0.0,
        minTemp = min_temp ?: 0.0,
        rainStatus = pop ?: 0,
        timeEpoch = ts ?: 0,
        icon = weather?.icon ?: ""
    )
}
fun List<Data>.asDomain():List<SixteenDayData>{
    return map{
        it.asDomain()
    }
}
