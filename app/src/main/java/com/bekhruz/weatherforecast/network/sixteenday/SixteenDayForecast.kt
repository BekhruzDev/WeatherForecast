package com.bekhruz.weatherforecast.network.sixteenday

data class SixteenDayForecast(
    val city_name: String,
    val country_code: String,
    val `data`: List<Data>,
    val lat: String,
    val lon: String,
    val state_code: String,
    val timezone: String
)