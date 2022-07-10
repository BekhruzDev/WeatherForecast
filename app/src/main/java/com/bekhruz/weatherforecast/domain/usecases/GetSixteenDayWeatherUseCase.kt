package com.bekhruz.weatherforecast.domain.usecases

import com.bekhruz.weatherforecast.data.repositories.SixteenDayWeatherRepository
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDayData
import javax.inject.Inject

interface GetSixteenDayWeatherUseCase {
    suspend operator fun invoke(
        latitude: String,
        longitude: String
    ): SixteenDayData
}

class GetSixteenDayWeatherUseCaseImpl @Inject constructor(
    private val sixteenDayWeatherRepository: SixteenDayWeatherRepository
) : GetSixteenDayWeatherUseCase {
    override suspend fun invoke(latitude: String, longitude: String): SixteenDayData {
        return sixteenDayWeatherRepository.fetchSixteenDayWeather(latitude, longitude)
    }
}