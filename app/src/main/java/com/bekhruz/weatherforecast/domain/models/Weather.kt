package com.bekhruz.weatherforecast.domain.models

import com.bekhruz.weatherforecast.data.remote.dto.currentweatherdto.Current
import com.bekhruz.weatherforecast.data.remote.dto.currentweatherdto.Forecast
import com.bekhruz.weatherforecast.data.remote.dto.currentweatherdto.Forecastday
import com.bekhruz.weatherforecast.data.remote.dto.currentweatherdto.Location

data class Weather(
    val current: Current,
    val forecastDay: List<Forecastday>,
    val location: Location
)