package com.bekhruz.weatherforecast.domain.models.currentweather

import com.bekhruz.weatherforecast.data.remote.dto.currentweather.Current
import com.bekhruz.weatherforecast.data.remote.dto.currentweather.Forecastday
import com.bekhruz.weatherforecast.data.remote.dto.currentweather.Location

data class Weather(
    val current: Current,
    val forecastDay: List<Forecastday>,
    val location: Location
)