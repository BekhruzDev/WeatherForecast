package com.bekhruz.weatherforecast.domain.models.currentweather

import com.bekhruz.weatherforecast.data.remote.dto.currentweather.Hour

data class ForecastDay(
    val dailyChanceOfRain: Int,
    val hour: List<Hour>,
)