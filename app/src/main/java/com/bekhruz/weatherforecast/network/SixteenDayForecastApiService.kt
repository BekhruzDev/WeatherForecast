package com.bekhruz.weatherforecast.network

import com.bekhruz.weatherforecast.network.sixteenday.SixteenDayForecast
import com.bekhruz.weatherforecast.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//moshi object
private val moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//retrofit object
private val retrofit = Retrofit
    .Builder()
    .baseUrl(Constants.BASE_URL_SIXTEENDAYS)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface SixteenDayApiService {
    @GET("forecast/daily")
    suspend fun getSixteenDayWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("key") apiKey: String
    ): Response<SixteenDayForecast>
}

object SixteenDayForecastApi {
    val retrofitService: SixteenDayApiService by lazy {
        retrofit.create(SixteenDayApiService::class.java)
    }
}
