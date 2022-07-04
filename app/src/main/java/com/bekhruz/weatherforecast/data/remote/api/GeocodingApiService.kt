package com.bekhruz.weatherforecast.data.remote.api

import com.bekhruz.weatherforecast.data.remote.dto.geocoding.Location
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Sample call: https://api.geoapify.com/v1/geocode/search?text=
tashkent&lang=en&limit=5&format=json&apiKey=5615726daad74e0e979da3b753d3aad9
 */
interface GeocodingApiService {
    @GET("geocode/search")
    suspend fun getFullLocationInfo(
        @Query("text") searchedLocation:String,
        @Query("lang") language:String,
        @Query("limit") resultsLimit:Int,
        @Query("type") type:String,
        @Query("format") responseFormat:String,
        @Query("apiKey") apiKey: String
    ):Location
}