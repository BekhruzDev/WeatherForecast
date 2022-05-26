package com.bekhruz.weatherforecast.network.sixteendayweather

data class Weather(
    val code: Int,
    val description: String,
    val icon: String
)