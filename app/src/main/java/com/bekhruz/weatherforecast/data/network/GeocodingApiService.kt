package com.bekhruz.weatherforecast.data.network

import com.bekhruz.weatherforecast.data.network.geocoding.Location
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

/**
 * Sample call: https://api.geoapify.com/v1/geocode/search?text=
tashkent&lang=en&limit=5&format=json&apiKey=5615726daad74e0e979da3b753d3aad9
 */
interface GeocodingApiService {
    @GET("geocode/search")
    suspend fun getFullLocationInfo(
        @Query("text") searchedLocation:String,
        @Query("lang") language:String,
        @Query("limit") resultsLimit:Int,
        @Query("type") type:String,
        @Query("format") responseFormat:String,
        @Query("apiKey") apiKey: String
    ):Response<Location>
}

object GeocodingApi{
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
        .baseUrl(Constants.BASE_URL_GEOCODING)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
    //retrofit service
    val retrofitService: GeocodingApiService by lazy {
        retrofit.create(GeocodingApiService::class.java)
    }
}
