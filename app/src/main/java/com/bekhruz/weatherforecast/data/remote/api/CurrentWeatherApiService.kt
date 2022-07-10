package com.bekhruz.weatherforecast.data.remote.api

import com.bekhruz.weatherforecast.data.remote.dto.current_weather_response.CurrentForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Sample Call for Current Weather data
 * https://api.weatherapi.com/v1/forecast.json?key=2024618787f64a0b816101809221805&q=London&days=7&aqi=no&alerts=no
 */
interface CurrentWeatherApiService {
    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey:String,
        @Query("q") latLon:String,
        @Query("days") days:Int,
        @Query("aqi") airQuality:String,
        @Query("alerts") alerts:String,
    ):CurrentForecastResponse
}

