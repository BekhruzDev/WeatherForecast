package com.bekhruz.weatherforecast.repositories


import com.bekhruz.weatherforecast.network.SixteenDayForecastApi
import com.bekhruz.weatherforecast.network.CurrentWeatherApi
import com.bekhruz.weatherforecast.network.currentweather.CurrentForecast
import com.bekhruz.weatherforecast.network.sixteendayweather.SixteenDayForecast
import com.bekhruz.weatherforecast.utils.Constants.API_KEY_CURRENT_WEATHER
import com.bekhruz.weatherforecast.utils.Constants.API_KEY_SIXTEEN_DAY_WEATHER
import retrofit2.Response

object Repositories {
    suspend fun getCurrentWeather(latLon: String): Response<CurrentForecast> {
        return CurrentWeatherApi.retrofitService.getCurrentWeather(
            API_KEY_CURRENT_WEATHER,
            latLon,
        )
    }

    suspend fun getSixteenDayWeather(latitude:String, longitude:String): Response<SixteenDayForecast> {
        return SixteenDayForecastApi.retrofitService.getSixteenDayWeather(
            latitude,
            longitude,
            API_KEY_SIXTEEN_DAY_WEATHER
        )
    }
}