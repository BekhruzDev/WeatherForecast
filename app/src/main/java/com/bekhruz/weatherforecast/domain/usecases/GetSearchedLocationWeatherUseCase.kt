package com.bekhruz.weatherforecast.domain.usecases

import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import com.bekhruz.weatherforecast.domain.models.geocoding.LocationResult
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocationData
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDayData
import javax.inject.Inject

interface GetSearchedLocationWeatherUseCase {
    suspend fun getSearchedLocationResults(location:String):SearchedLocationData
    suspend operator fun invoke(locationResult: LocationResult): Pair<CurrentWeatherData, SixteenDayData>
}

class GetSearchedLocationWeatherUseCaseImpl
@Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getSixteenDayWeatherUseCase: GetSixteenDayWeatherUseCase,
    private val getGeocodingLocationUseCase: GetGeocodingLocationUseCase
):GetSearchedLocationWeatherUseCase{
    override suspend fun invoke(locationResult: LocationResult): Pair<CurrentWeatherData, SixteenDayData> {
        val currentWeather = getCurrentWeatherUseCase(locationResult.locationName)
        val sixteenDayWeather = getSixteenDayWeatherUseCase(locationResult.lat.toString(), locationResult.lon.toString())
        return Pair(currentWeather, sixteenDayWeather)
    }

    override suspend fun getSearchedLocationResults(location: String): SearchedLocationData {
        return getGeocodingLocationUseCase(location)
    }

}