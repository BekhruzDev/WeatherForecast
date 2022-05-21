package com.bekhruz.weatherforecast.network

import com.bekhruz.weatherforecast.network.sevenday.SevenDayForecast
import com.bekhruz.weatherforecast.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Sample Call:
 * https://api.weatherapi.com/v1/forecast.json?key=2024618787f64a0b816101809221805&q=London&days=7&aqi=no&alerts=no
 */
//moshi object
private val moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//retrofit object
private val retrofit = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getSevenDayWeather(
        @Query("key") apiKey:String,
        @Query("q") location:String,
        @Query("days") days:Int = 7,
        @Query("aqi") airQuality:String = "no",
        @Query("alerts") alerts:String = "no",
    ):Response<SevenDayForecast>
}

object WeatherApi{
    val retrofitService:WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}