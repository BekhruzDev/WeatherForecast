package com.bekhruz.weatherforecast.data.repositories

import com.bekhruz.weatherforecast.data.remote.retrofitservice.DemoRequestApi
import com.bekhruz.weatherforecast.data.remote.utils.Constants
import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import retrofit2.Response
import javax.inject.Inject


interface DemoRequestRepository {
    suspend fun fetchCurrentWeather(): Response<CurrentWeatherData>
}

class DemoRequestRepositoryImpl
@Inject constructor() : DemoRequestRepository {
    override suspend fun fetchCurrentWeather(): Response<CurrentWeatherData> {
        return DemoRequestApi.retrofitService.getCurrentWeather(
            Constants.API_KEY_CURRENT_WEATHER,
            "london",
            3,
            "no",
            "no"
        )
    }
}