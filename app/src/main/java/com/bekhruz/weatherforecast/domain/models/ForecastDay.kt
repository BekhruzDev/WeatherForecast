package com.bekhruz.weatherforecast.domain.models

import com.bekhruz.weatherforecast.data.remote.dto.currentweatherdto.Hour

data class ForecastDay(
    val dailyChanceOfRain: Int,
    val hour: List<Hour>,
)