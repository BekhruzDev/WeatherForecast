package com.bekhruz.weatherforecast.data.network.geocoding

data class Location(
    val query: Query?,
    val results: List<Result>
)