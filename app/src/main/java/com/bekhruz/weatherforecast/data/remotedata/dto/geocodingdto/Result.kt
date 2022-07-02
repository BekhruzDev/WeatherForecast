package com.bekhruz.weatherforecast.data.remotedata.dto.geocodingdto

data class Result(
    val address_line1: String?,
    val address_line2: String?,
    val bbox: Bbox?,
    val category: String?,
    val city: String?,
    val country: String?,
    val country_code: String?,
    val county: String?,
    val datasource: Datasource?,
    val formatted: String?,
    val hamlet: String?,
    val lat: Double?,
    val lon: Double?,
    val name: String?,
    val old_name: String?,
    val place_id: String?,
    val postcode: String?,
    val rank: Rank?,
    val result_type: String?,
    val state: String?,
    val street: String?,
    val suburb: String?
)