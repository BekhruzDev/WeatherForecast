package com.bekhruz.weatherforecast.data.remotedata.dto.currentweatherdto

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)