package com.bekhruz.weatherforecast.data.remotedata.dto.geocodingdto

data class Location(
    val query: Query?,
    val results: List<Result>
)