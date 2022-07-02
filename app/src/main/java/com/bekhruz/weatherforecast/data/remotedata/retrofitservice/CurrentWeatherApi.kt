package com.bekhruz.weatherforecast.data.remotedata.retrofitservice

import com.bekhruz.weatherforecast.data.remotedata.api.CurrentWeatherApiService
import com.bekhruz.weatherforecast.data.remotedata.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CurrentWeatherApi{
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
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    //retrofit object
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_CURRENT_WEATHER)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    //retrofit service
    val retrofitService: CurrentWeatherApiService by lazy {
        retrofit.create(CurrentWeatherApiService::class.java)
    }
}