package com.bekhruz.weatherforecast.domain.models.sixteendayweather


data class SixteenDayData(
    val dailyForecasts : List<DailyForecast>
)
data class DailyForecast(
    val maxTemp: String,
    val minTemp: String,
    val rainStatus: String,
    val time: String,
    val icon: String
)
