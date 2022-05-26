package com.bekhruz.weatherforecast.network.currentweather

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)