package com.bekhruz.weatherforecast.data.repositories

import com.bekhruz.weatherforecast.data.remote.dto.geocoding_response.asDomain
import com.bekhruz.weatherforecast.data.remote.retrofitservice.GeocodingApi
import com.bekhruz.weatherforecast.data.remote.utils.Constants
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocationData
import javax.inject.Inject

interface GeocodingLocationRepository {
    suspend fun fetchGeocodingLocation(location: String): SearchedLocationData
}

class GeocodingLocationRepositoryImpl
@Inject constructor() : GeocodingLocationRepository {
    override suspend fun fetchGeocodingLocation(location: String): SearchedLocationData {
        return GeocodingApi.retrofitService.getFullLocationInfo(
            location,
            "en",
            10,
            "city",
            "json",
            Constants.API_KEY_GEOCODING
        ).asDomain()
    }
}