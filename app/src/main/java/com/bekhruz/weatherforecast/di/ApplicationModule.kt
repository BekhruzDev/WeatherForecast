package com.bekhruz.weatherforecast.di

import com.bekhruz.weatherforecast.repositories.WeatherRepository
import com.bekhruz.weatherforecast.repositories.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Singleton
    @Binds
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl):WeatherRepository
}