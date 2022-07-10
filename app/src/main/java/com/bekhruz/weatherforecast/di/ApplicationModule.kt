package com.bekhruz.weatherforecast.di

import android.content.Context
import com.bekhruz.weatherforecast.data.repositories.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Singleton
    @Provides
     fun providesCurrentWeatherRepository(): CurrentWeatherRepository {
         return CurrentWeatherRepositoryImpl()
     }
    @Singleton
    @Provides
     fun providesGeocodingLocationRepository(): GeocodingLocationRepository {
         return GeocodingLocationRepositoryImpl()
     }
    @Singleton
    @Provides
     fun providesSixteenDayWeatherRepository():SixteenDayWeatherRepository{
         return SixteenDayWeatherRepositoryImpl()
     }

    @Provides
    fun providesFusedLocationProviderClient(
        @ApplicationContext appContext: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }
}