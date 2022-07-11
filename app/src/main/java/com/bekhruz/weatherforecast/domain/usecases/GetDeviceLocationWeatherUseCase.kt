package com.bekhruz.weatherforecast.domain.usecases


import android.annotation.SuppressLint
import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDayData
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


interface GetDeviceLocationWeatherUseCase {
    suspend operator fun invoke(): Pair<CurrentWeatherData, SixteenDayData>
}

class GetDeviceLocationWeatherUseCaseImpl
@Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getSixteenDayWeatherUseCase: GetSixteenDayWeatherUseCase
) : GetDeviceLocationWeatherUseCase {

    @SuppressLint("MissingPermission")
    override suspend operator fun invoke(): Pair<CurrentWeatherData, SixteenDayData> {
        val result = fusedLocationProviderClient.lastLocation.await()
        val currentWeather = getCurrentWeatherUseCase("${result.latitude},${result.longitude}")
        val sixteenDayWeather = getSixteenDayWeatherUseCase(result.latitude.toString(), result.longitude.toString())
        return Pair(currentWeather, sixteenDayWeather)
    }
}

