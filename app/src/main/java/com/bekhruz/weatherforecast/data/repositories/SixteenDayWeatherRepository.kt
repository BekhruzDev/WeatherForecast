package com.bekhruz.weatherforecast.data.repositories

import com.bekhruz.weatherforecast.data.remote.dto.sixteenday_weather_response.asDomain
import com.bekhruz.weatherforecast.data.remote.retrofitservice.SixteenDayForecastApi
import com.bekhruz.weatherforecast.data.remote.utils.Constants
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDayData
import javax.inject.Inject

interface SixteenDayWeatherRepository {
    suspend fun fetchSixteenDayWeather(
        latitude: String,
        longitude: String
    ): SixteenDayData
}

class SixteenDayWeatherRepositoryImpl
@Inject constructor() : SixteenDayWeatherRepository {
    override suspend fun fetchSixteenDayWeather(
        latitude: String,
        longitude: String
    ): SixteenDayData {
        return SixteenDayForecastApi.retrofitService.getSixteenDayWeather(
            latitude,
            longitude,
            Constants.API_KEY_SIXTEEN_DAY_WEATHER
        ).asDomain()
    }
}