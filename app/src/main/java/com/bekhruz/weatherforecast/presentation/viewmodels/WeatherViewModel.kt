package com.bekhruz.weatherforecast.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocation
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDay
import com.bekhruz.weatherforecast.domain.models.home.CurrentWeatherData
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

    private val _sixteenDayWeatherData = MutableLiveData<SixteenDay>()
    val sixteenDayWeatherData: LiveData<SixteenDay> = _sixteenDayWeatherData
    private val _searchedLocation = MutableLiveData<SearchedLocation>()
    val searchedLocation: LiveData<SearchedLocation> = _searchedLocation
    private val _currentWeatherData = MutableLiveData<CurrentWeatherData>()
    val currentWeatherData: LiveData<CurrentWeatherData> = _currentWeatherData

    private fun getCurrentWeather(latLon: String) {
        vmScope.loadingLaunch {
            val response = getCurrentWeatherUseCase(latLon)
            _currentWeatherData.postValue(response)
        }
    }

    private fun getSixteenDayWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            val response = getSixteenDayWeatherUseCase(latitude, longitude)
            _sixteenDayWeatherData.value = response
        }
    }

    fun getSearchedLocation(searchedLocation: String): LiveData<SearchedLocation> {
        viewModelScope.launch {
            val response = getGeocodingLocationUseCase(searchedLocation)
            _searchedLocation.value = response
        }
        return _searchedLocation
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocationData() {
        vmScope.launch {
            val currentWeatherAtDeviceLocation = getDeviceLocationUseCase().first
            val sixteenDayWeatherAtDeviceLocation = getDeviceLocationUseCase().second
            _currentWeatherData.postValue(currentWeatherAtDeviceLocation)
            _sixteenDayWeatherData.postValue(sixteenDayWeatherAtDeviceLocation)
        }
    }
}