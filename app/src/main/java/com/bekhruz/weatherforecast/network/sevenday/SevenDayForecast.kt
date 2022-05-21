package com.bekhruz.weatherforecast.network.sevenday

data class SevenDayForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)