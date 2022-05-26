package com.bekhruz.weatherforecast.network.currentweather

data class CurrentForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)