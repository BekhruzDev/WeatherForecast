package com.bekhruz.weatherforecast.domain.usecases

import com.bekhruz.weatherforecast.data.repositories.SixteenDayWeatherRepository
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDay
import javax.inject.Inject

interface GetSixteenDayWeatherUseCase {
    suspend operator fun invoke(
        latitude: String,
        longitude: String
    ): SixteenDay
}

class GetSixteenDayWeatherUseCaseImpl @Inject constructor(
    private val sixteenDayWeatherRepository: SixteenDayWeatherRepository
) : GetSixteenDayWeatherUseCase {
    override suspend fun invoke(latitude: String, longitude: String): SixteenDay {
        return sixteenDayWeatherRepository.fetchSixteenDayWeather(latitude, longitude)
    }
}