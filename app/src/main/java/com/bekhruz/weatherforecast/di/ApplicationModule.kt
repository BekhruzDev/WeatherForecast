package com.bekhruz.weatherforecast.di

import android.content.Context
import com.bekhruz.weatherforecast.data.repositories.WeatherRepository
import com.bekhruz.weatherforecast.data.repositories.WeatherRepositoryImpl
import com.bekhruz.weatherforecast.utils.FusedLocationLibrary
import com.bekhruz.weatherforecast.utils.FusedLocationLibraryImpl
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
     fun providesWeatherRepository():WeatherRepository{
         return WeatherRepositoryImpl()
     }

    @Provides
    fun providesFusedLocationProviderClient(
        @ApplicationContext appContext: Context
    ): FusedLocationLibrary {
        return FusedLocationLibraryImpl(appContext)
    }
}