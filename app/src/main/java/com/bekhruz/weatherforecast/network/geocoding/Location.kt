package com.bekhruz.weatherforecast.network.geocoding

data class Location(
    val query: Query,
    val results: List<Result>
)