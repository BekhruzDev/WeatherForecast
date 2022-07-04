package com.bekhruz.weatherforecast.data.repositories

import com.bekhruz.weatherforecast.data.remote.dto.sixteendayweather.asDomain
import com.bekhruz.weatherforecast.data.remote.retrofitservice.SixteenDayForecastApi
import com.bekhruz.weatherforecast.data.remote.utils.Constants
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDay
import javax.inject.Inject

interface SixteenDayWeatherRepository {
    suspend fun fetchSixteenDayWeather(
        latitude: String,
        longitude: String
    ): SixteenDay
}

class SixteenDayWeatherRepositoryImpl
@Inject constructor() : SixteenDayWeatherRepository {
    override suspend fun fetchSixteenDayWeather(
        latitude: String,
        longitude: String
    ): SixteenDay {
        return SixteenDayForecastApi.retrofitService.getSixteenDayWeather(
            latitude,
            longitude,
            Constants.API_KEY_SIXTEEN_DAY_WEATHER
        ).asDomain()
    }
}