package com.bekhruz.weatherforecast.domain.models.geocoding


data class SearchedLocationData(
    val locationResults:List<LocationResult>
    )

data class LocationResult(
    val locationInfo:String,
    val locationName: String,
    val lat: Double,
    val lon: Double,
)

