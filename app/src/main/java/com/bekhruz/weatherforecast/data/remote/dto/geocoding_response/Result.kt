package com.bekhruz.weatherforecast.data.remote.dto.geocoding_response

import com.bekhruz.weatherforecast.domain.models.geocoding.LocationResult

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
fun Result.asDomain(): LocationResult {
        return LocationResult(
            locationInfo = String.format("%s, %s",address_line1?:"",country?:""),
            locationName = name?:address_line1?:"",
            lat = lat?:0.0,
            lon = lon?:0.0
        )
    }
fun List<Result>.asDomain():List<LocationResult>{
    return map{
        it.asDomain()
    }
}