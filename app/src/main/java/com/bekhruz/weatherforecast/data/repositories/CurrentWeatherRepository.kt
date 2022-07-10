package com.bekhruz.weatherforecast.data.repositories

import com.bekhruz.weatherforecast.data.remote.dto.current_weather_response.asDomain
import com.bekhruz.weatherforecast.data.remote.retrofitservice.CurrentWeatherApi
import com.bekhruz.weatherforecast.data.remote.utils.Constants
import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import javax.inject.Inject

interface CurrentWeatherRepository {
    suspend fun fetchCurrentWeather(latLon: String): CurrentWeatherData
}

class CurrentWeatherRepositoryImpl
@Inject constructor() : CurrentWeatherRepository {
    override suspend fun fetchCurrentWeather(latLon: String): CurrentWeatherData {
        return CurrentWeatherApi.retrofitService.getCurrentWeather(
            Constants.API_KEY_CURRENT_WEATHER,
            latLon,
            3,
            "no",
            "no"
        ).asDomain()
    }
}