package com.bekhruz.weatherforecast.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocationData
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDayData
import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import com.bekhruz.weatherforecast.domain.usecases.GetCurrentWeatherUseCase
import com.bekhruz.weatherforecast.domain.usecases.GetDeviceLocationUseCase
import com.bekhruz.weatherforecast.domain.usecases.GetGeocodingLocationUseCase
import com.bekhruz.weatherforecast.domain.usecases.GetSixteenDayWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.bekhruz.weatherforecast.presentation.core.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor() : BaseViewModel() {
    //useCases
    @Inject lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    @Inject lateinit var getGeocodingLocationUseCase: GetGeocodingLocationUseCase
    @Inject lateinit var getSixteenDayWeatherUseCase: GetSixteenDayWeatherUseCase
    @Inject lateinit var getDeviceLocationUseCase: GetDeviceLocationUseCase

    private val _sixteenDayWeatherData = MutableLiveData<SixteenDayData>()
    val sixteenDayWeatherData: LiveData<SixteenDayData> = _sixteenDayWeatherData
    private val _searchedLocation = MutableLiveData<SearchedLocationData>()
    val searchedLocation: LiveData<SearchedLocationData> = _searchedLocation
    private val _currentWeatherData = MutableLiveData<CurrentWeatherData>()
    val currentWeatherData: LiveData<CurrentWeatherData> = _currentWeatherData

    private fun getCurrentWeather(latLon: String) {
        vmScope.loadingLaunch {
            val response = getCurrentWeatherUseCase(latLon)
            _currentWeatherData.postValue(response)
        }
    }

    private fun getSixteenDayWeather(latitude: String, longitude: String) {
        vmScope.loadingLaunch {
            val response = getSixteenDayWeatherUseCase(latitude, longitude)
            _sixteenDayWeatherData.postValue(response)
        }
    }

    fun getSearchedLocation(searchedLocation: String): LiveData<SearchedLocationData> {
        vmScope.loadingLaunch {
            val response = getGeocodingLocationUseCase(searchedLocation)
            _searchedLocation.postValue(response)
        }
        return _searchedLocation
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocationData() {
        vmScope.loadingLaunch {
            val currentWeatherAtDeviceLocation = getDeviceLocationUseCase().first
            val sixteenDayWeatherAtDeviceLocation = getDeviceLocationUseCase().second
            _currentWeatherData.postValue(currentWeatherAtDeviceLocation)
            _sixteenDayWeatherData.postValue(sixteenDayWeatherAtDeviceLocation)
        }
    }
}