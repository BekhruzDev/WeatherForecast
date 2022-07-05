package com.bekhruz.weatherforecast.domain.usecases

import com.bekhruz.weatherforecast.data.repositories.CurrentWeatherRepository
import com.bekhruz.weatherforecast.domain.models.currentweather.Weather
import com.bekhruz.weatherforecast.domain.models.home.HomeWeatherData
import javax.inject.Inject

interface GetCurrentWeatherUseCase {
    suspend operator fun invoke(latLon: String): HomeWeatherData
}

class GetCurrentWeatherUseCaseImpl
@Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository
) : GetCurrentWeatherUseCase {
    override suspend fun invoke(latLon: String): HomeWeatherData {
     return currentWeatherRepository.fetchCurrentWeather(latLon)
    }
}