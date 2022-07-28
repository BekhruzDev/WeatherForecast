package com.bekhruz.weatherforecast.data.remote.retrofitservice

import com.bekhruz.weatherforecast.data.remote.HttpLoggingInterceptor
import com.bekhruz.weatherforecast.data.remote.api.SixteenDayApiService
import com.bekhruz.weatherforecast.data.remote.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SixteenDayForecastApi {
    //OkHttpClient
    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().invoke())
        .build()

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
