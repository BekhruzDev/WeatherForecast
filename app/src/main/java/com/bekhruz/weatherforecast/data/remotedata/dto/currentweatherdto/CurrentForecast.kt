package com.bekhruz.weatherforecast.data.remotedata.dto.currentweatherdto

data class CurrentForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)