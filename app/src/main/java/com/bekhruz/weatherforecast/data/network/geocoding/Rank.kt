package com.bekhruz.weatherforecast.data.network.geocoding

data class Rank(
    val confidence: Int?,
    val confidence_city_level: Int?,
    val importance: Double?,
    val match_type: String?,
    val popularity: Double?
)