package com.bekhruz.weatherforecast.data.remote.dto.geocoding

import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocation

data class Location(
    val query: Query?,
    val results: List<Result>?
)

fun Location.asDomain(): SearchedLocation {
    return SearchedLocation(results = results ?: listOf())
}
