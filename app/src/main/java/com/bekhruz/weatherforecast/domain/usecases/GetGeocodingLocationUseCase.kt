package com.bekhruz.weatherforecast.domain.usecases

import com.bekhruz.weatherforecast.data.repositories.GeocodingLocationRepository
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocation
import javax.inject.Inject

interface GetGeocodingLocationUseCase {
    suspend operator fun invoke(location: String): SearchedLocation
}

class GetGeocodingLocationUseCaseImpl @Inject constructor(
    private val geocodingLocationRepository: GeocodingLocationRepository
) : GetGeocodingLocationUseCase {
    override suspend fun invoke(location: String): SearchedLocation {
        return geocodingLocationRepository.fetchGeocodingLocation(location)
    }
}