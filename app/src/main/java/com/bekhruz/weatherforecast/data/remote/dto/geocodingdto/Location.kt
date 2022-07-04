package com.bekhruz.weatherforecast.data.remote.dto.geocodingdto

import com.bekhruz.weatherforecast.data.remote.utils.Mapper
import com.bekhruz.weatherforecast.domain.models.SearchedLocation

data class Location(
    val query: Query?,
    val results: List<Result>?
)

fun Location.asDomain(): SearchedLocation {
    return SearchedLocation(results = results ?: listOf())
}
