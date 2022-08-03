package com.bekhruz.weatherforecast.di

import com.bekhruz.weatherforecast.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Singleton
    @Binds
    abstract fun bindGetCurrentWeatherUseCase(
        impl: GetCurrentWeatherUseCaseImpl
    ): GetCurrentWeatherUseCase

    @Singleton
    @Binds
    abstract fun bindGetSixteenDayWeatherUseCase(
        impl: GetSixteenDayWeatherUseCaseImpl
    ): GetSixteenDayWeatherUseCase

    @Singleton
    @Binds
    abstract fun bindGetGeocodingLocationUseCase(
        impl: GetGeocodingLocationUseCaseImpl
    ): GetGeocodingLocationUseCase

    @Singleton
    @Binds
    abstract fun bindGetDeviceLocationWeatherUseCase(
        impl: GetDeviceLocationWeatherUseCaseImpl
    ): GetDeviceLocationWeatherUseCase

    @Singleton
    @Binds
    abstract fun bindGetSearchedLocationWeatherUseCase(
        impl: GetSearchedLocationWeatherUseCaseImpl
    ): GetSearchedLocationWeatherUseCase


}