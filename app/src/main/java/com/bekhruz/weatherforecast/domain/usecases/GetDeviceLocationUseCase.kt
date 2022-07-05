package com.bekhruz.weatherforecast.domain.usecases

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.bekhruz.weatherforecast.domain.models.home.HomeWeatherData
import com.bekhruz.weatherforecast.utils.FusedLocationLibrary
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

interface GetDeviceLocationUseCase {
    suspend operator fun invoke(): HomeWeatherData
}

class GetDeviceLocationUseCaseImpl
@Inject constructor(
    private val fusedLocationProviderClient: FusedLocationLibrary,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getSixteenDayWeatherUseCase: GetSixteenDayWeatherUseCase
) : GetDeviceLocationUseCase {

    @SuppressLint("MissingPermission")
    override suspend operator fun invoke(): HomeWeatherData {
        val result =
            fusedLocationProviderClient.getFusedLocationProviderClient().lastLocation.await()
        val currentWeather = getCurrentWeatherUseCase("${result.latitude},${result.longitude}")
        return currentWeather
    }
}