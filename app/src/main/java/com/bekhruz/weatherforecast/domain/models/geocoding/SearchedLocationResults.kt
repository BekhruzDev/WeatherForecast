package com.bekhruz.weatherforecast.domain.models.geocoding

data class SearchedLocationResults(
    val addressLine1: String,
    val country: String,
    val name: String,
    val lat: Double,
    val lon: Double,
)
