package com.bekhruz.weatherforecast.data.remote.api

import com.bekhruz.weatherforecast.data.remote.dto.current_weather_response.CurrentForecastResponse
import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DemoRequestApiService {
        @GET("forecast.json")
        suspend fun getCurrentWeather(
            @Query("key") apiKey:String,
            @Query("q") latLon:String,
            @Query("days") days:Int,
            @Query("aqi") airQuality:String,
            @Query("alerts") alerts:String,
        ): Response<CurrentWeatherData>
}
