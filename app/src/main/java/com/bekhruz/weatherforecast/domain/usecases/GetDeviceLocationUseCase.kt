package com.bekhruz.weatherforecast.domain.usecases


import android.annotation.SuppressLint
import com.bekhruz.weatherforecast.domain.models.home.CurrentWeatherData
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDay
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


interface GetDeviceLocationUseCase {
    suspend operator fun invoke(): Pair<CurrentWeatherData, SixteenDay>
}

class GetDeviceLocationUseCaseImpl
@Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getSixteenDayWeatherUseCase: GetSixteenDayWeatherUseCase
) : GetDeviceLocationUseCase {

    @SuppressLint("MissingPermission")
    override suspend operator fun invoke(): Pair<CurrentWeatherData, SixteenDay> {
        val result = fusedLocationProviderClient.lastLocation.await()
        val currentWeather = getCurrentWeatherUseCase("${result.latitude},${result.longitude}")
        val sixteenDayWeather = getSixteenDayWeatherUseCase(result.latitude.toString(), result.longitude.toString())
        return Pair(currentWeather, sixteenDayWeather)
    }
}

