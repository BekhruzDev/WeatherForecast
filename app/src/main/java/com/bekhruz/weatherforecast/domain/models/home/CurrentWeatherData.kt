package com.bekhruz.weatherforecast.domain.models.home

data class CurrentWeatherData(
    val icon: String,
    val text: String,
    val humidity: String,
    val pressureMb: String,
    val windKph: String,
    val dailyChanceOfRain: String,
    val hourlyData: List<Hourly>,
    val tempC: String,
    val name: String,
    val lastUpdateDate: String,
    val lat: Double,
    val lon: Double
)

data class Hourly(
    val time: String,
    val icon: String,
    val tempC: String,
    val chanceOfRain: String,
)