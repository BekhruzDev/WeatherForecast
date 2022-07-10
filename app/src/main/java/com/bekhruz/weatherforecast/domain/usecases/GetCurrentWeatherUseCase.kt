package com.bekhruz.weatherforecast.domain.usecases

import com.bekhruz.weatherforecast.data.repositories.CurrentWeatherRepository
import com.bekhruz.weatherforecast.domain.models.home.CurrentWeatherData
import javax.inject.Inject

interface GetCurrentWeatherUseCase {
    suspend operator fun invoke(latLon: String): CurrentWeatherData
}

class GetCurrentWeatherUseCaseImpl
@Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository
) : GetCurrentWeatherUseCase {
    override suspend fun invoke(latLon: String): CurrentWeatherData {
     return currentWeatherRepository.fetchCurrentWeather(latLon)
    }
}