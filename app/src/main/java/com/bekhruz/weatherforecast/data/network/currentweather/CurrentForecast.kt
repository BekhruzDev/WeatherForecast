package com.bekhruz.weatherforecast.data.network.currentweather

data class CurrentForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)