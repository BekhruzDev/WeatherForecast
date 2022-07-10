package com.bekhruz.weatherforecast.domain.usecases

import com.bekhruz.weatherforecast.data.repositories.GeocodingLocationRepository
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocationData
import javax.inject.Inject

interface GetGeocodingLocationUseCase {
    suspend operator fun invoke(location: String): SearchedLocationData
}

class GetGeocodingLocationUseCaseImpl @Inject constructor(
    private val geocodingLocationRepository: GeocodingLocationRepository
) : GetGeocodingLocationUseCase {
    override suspend fun invoke(location: String): SearchedLocationData {
        return geocodingLocationRepository.fetchGeocodingLocation(location)
    }
}