package com.bekhruz.weatherforecast.network

import com.bekhruz.weatherforecast.network.currentweather.CurrentForecast
import com.bekhruz.weatherforecast.utils.Constants.BASE_URL_CURRENT_WEATHER
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Sample Call for Current Weather data
 * https://api.weatherapi.com/v1/forecast.json?key=2024618787f64a0b816101809221805&q=London&days=7&aqi=no&alerts=no
 */

//logging interceptor
private val interceptor = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}
//OkHttpClient
private val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

//moshi object
private val moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//retrofit object
private val retrofit = Retrofit
    .Builder()
    .baseUrl(BASE_URL_CURRENT_WEATHER)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .build()

interface CurrentWeatherApiService {
    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey:String,
        @Query("q") latLon:String,
        @Query("days") days:Int = 3,
        @Query("aqi") airQuality:String = "no",
        @Query("alerts") alerts:String = "no",
    ):Response<CurrentForecast>
}

object CurrentWeatherApi{
    val retrofitService:CurrentWeatherApiService by lazy {
        retrofit.create(CurrentWeatherApiService::class.java)
    }
}