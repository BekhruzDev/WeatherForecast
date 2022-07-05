package com.bekhruz.weatherforecast.presentation.viewmodels

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocation
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDay
import com.bekhruz.weatherforecast.domain.models.currentweather.Weather
import com.bekhruz.weatherforecast.domain.models.home.HomeWeatherData
import com.bekhruz.weatherforecast.domain.usecases.GetCurrentWeatherUseCase
import com.bekhruz.weatherforecast.domain.usecases.GetDeviceLocationUseCase
import com.bekhruz.weatherforecast.domain.usecases.GetGeocodingLocationUseCase
import com.bekhruz.weatherforecast.domain.usecases.GetSixteenDayWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.bekhruz.weatherforecast.presentation.core.BaseViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: GetCurrentWeatherUseCase,
    private val geocodingLocationUseCase: GetGeocodingLocationUseCase,
    private val sixteenDayWeatherUseCase: GetSixteenDayWeatherUseCase,
    private val getDeviceLocationUseCase: GetDeviceLocationUseCase
) : BaseViewModel() {
    private val _currentWeatherData = MutableLiveData<Weather>()
    val currentWeatherData: LiveData<Weather> = _currentWeatherData
    private val _sixteenDayWeatherData = MutableLiveData<SixteenDay>()
    val sixteenDayWeatherData: LiveData<SixteenDay> = _sixteenDayWeatherData
    private val _searchedLocation = MutableLiveData<SearchedLocation>()
    val searchedLocation: LiveData<SearchedLocation> = _searchedLocation

    private val _homeWeatherData = MutableLiveData<HomeWeatherData>()
    val homeWeatherData: LiveData<HomeWeatherData> = _homeWeatherData

    private fun getCurrentWeather(latLon: String) {
        vmScope.loadingLaunch {
//            val response = currentWeatherUseCase(latLon)
//            _currentWeatherData.value = response
        }
    }

    private fun getSixteenDayWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            val response = sixteenDayWeatherUseCase(latitude, longitude)
            _sixteenDayWeatherData.value = response
        }
    }

    fun getSearchedLocationInfo(searchedLocation: String): LiveData<SearchedLocation> {
        viewModelScope.launch {
            val response = geocodingLocationUseCase(searchedLocation)
            _searchedLocation.value = response
        }
        return _searchedLocation
    }

    fun getDeviceLocationData() {
        vmScope.launch {
            val homeWeatherData = getDeviceLocationUseCase()
            _homeWeatherData.postValue(homeWeatherData)
        }
    }

    companion object {
        private const val TAG = "WEATHER VIEW MODEL"
    }
}