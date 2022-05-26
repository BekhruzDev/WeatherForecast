package com.bekhruz.weatherforecast.repositories


import com.bekhruz.weatherforecast.network.SixteenDayForecastApi
import com.bekhruz.weatherforecast.network.WeatherApi
import com.bekhruz.weatherforecast.network.sevenday.SevenDayForecast
import com.bekhruz.weatherforecast.network.sixteenday.SixteenDayForecast
import com.bekhruz.weatherforecast.utils.Constants.API_KEY
import com.bekhruz.weatherforecast.utils.Constants.API_KEY_SIXTEENDAYS
import retrofit2.Response

object Repositories {
    suspend fun getSevenDayWeather(latLon: String): Response<SevenDayForecast> {
        return WeatherApi.retrofitService.getSevenDayWeather(
            API_KEY,
            latLon,
        )
    }

    suspend fun getSixteenDayWeather(latitude:String, longitude:String): Response<SixteenDayForecast> {
        return SixteenDayForecastApi.retrofitService.getSixteenDayWeather(
            latitude,
            longitude,
            API_KEY_SIXTEENDAYS
        )
    }
}