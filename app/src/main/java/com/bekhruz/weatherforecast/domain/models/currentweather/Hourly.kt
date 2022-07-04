package com.bekhruz.weatherforecast.domain.models.currentweather

data class Hourly(
    val timeEpoch: Int,
    val icon: String,
    val tempC: Double,
    val chanceOfRain: Int,
    )
