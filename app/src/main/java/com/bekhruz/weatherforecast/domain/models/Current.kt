package com.bekhruz.weatherforecast.domain.models


data class Current(
    val icon: String,
    val text: String,
    val humidity: Int,
    val lastUpdatedEpoch: Int,
    val pressureMb: Double,
    val tempC: Double,
    val windKph: Double,
    )
