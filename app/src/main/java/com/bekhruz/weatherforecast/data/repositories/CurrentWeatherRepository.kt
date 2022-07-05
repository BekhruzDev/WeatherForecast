package com.bekhruz.weatherforecast.data.repositories

import com.bekhruz.weatherforecast.data.remote.dto.currentweather.asDomain
import com.bekhruz.weatherforecast.data.remote.retrofitservice.CurrentWeatherApi
import com.bekhruz.weatherforecast.data.remote.utils.Constants
import com.bekhruz.weatherforecast.domain.models.currentweather.Weather
import com.bekhruz.weatherforecast.domain.models.home.HomeWeatherData
import javax.inject.Inject

interface CurrentWeatherRepository {
    suspend fun fetchCurrentWeather(latLon: String): HomeWeatherData
}

class CurrentWeatherRepositoryImpl
@Inject constructor() : CurrentWeatherRepository {
    override suspend fun fetchCurrentWeather(latLon: String): HomeWeatherData {
        return CurrentWeatherApi.retrofitService.getCurrentWeather(
            Constants.API_KEY_CURRENT_WEATHER,
            latLon,
            3,
            "no",
            "no"
        ).asDomain()
    }
}