package com.bekhruz.weatherforecast.network

import com.bekhruz.weatherforecast.network.sixteendayweather.SixteenDayForecast
import com.bekhruz.weatherforecast.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SixteenDayApiService {
    @GET("forecast/daily")
    suspend fun getSixteenDayWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("key") apiKey: String
    ): Response<SixteenDayForecast>
}

object SixteenDayForecastApi {
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
        .baseUrl(Constants.BASE_URL_SIXTEEN_DAY_WEATHER)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
    //retrofit service
    val retrofitService: SixteenDayApiService by lazy {
        retrofit.create(SixteenDayApiService::class.java)
    }
}
