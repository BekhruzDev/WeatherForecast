package com.bekhruz.weatherforecast.domain.models

data class SixteenDayData(
    val maxTemp: Double,
    val minTemp: Double,
    val rainStatus: Int,
    val timeEpoch: Int,
    val icon: String
    )
