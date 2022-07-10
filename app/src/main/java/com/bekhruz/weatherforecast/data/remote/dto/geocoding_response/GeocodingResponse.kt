package com.bekhruz.weatherforecast.data.remote.dto.geocoding_response

import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocationData

data class GeocodingResponse(
    val query: Query?,
    val results: List<Result>?
)

fun GeocodingResponse.asDomain(): SearchedLocationData {
    return SearchedLocationData (
     locationResults = results?.asDomain()?: listOf()
    )
}
