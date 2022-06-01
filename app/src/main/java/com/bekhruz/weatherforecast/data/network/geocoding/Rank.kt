package com.bekhruz.weatherforecast.data.network.geocoding

data class Rank(
    val confidence: Double?,
    val confidence_city_level: Double?,
    val importance: Double?,
    val match_type: String?,
    val popularity: Double?
)