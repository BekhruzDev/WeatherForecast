package com.bekhruz.weatherforecast.domain.usecases

import com.bekhruz.weatherforecast.data.repositories.DemoRequestRepository
import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import retrofit2.Response
import javax.inject.Inject

interface DoDemoRequestUseCase {
    suspend operator fun invoke(): Response<CurrentWeatherData>
}

class DoDemoRequestUseCaseImpl
@Inject constructor(
    private val demoRequestRepository: DemoRequestRepository
) : DoDemoRequestUseCase {
    override suspend fun invoke(): Response<CurrentWeatherData> {
        return demoRequestRepository.fetchCurrentWeather()
    }
}