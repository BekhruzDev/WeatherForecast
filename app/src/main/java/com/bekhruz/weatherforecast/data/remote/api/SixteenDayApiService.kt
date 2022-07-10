package com.bekhruz.weatherforecast.data.remote.api

import com.bekhruz.weatherforecast.data.remote.dto.sixteenday_weather_response.SixteenDayForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SixteenDayApiService {
    @GET("forecast/daily")
    suspend fun getSixteenDayWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("key") apiKey: String
    ):SixteenDayForecastResponse
}