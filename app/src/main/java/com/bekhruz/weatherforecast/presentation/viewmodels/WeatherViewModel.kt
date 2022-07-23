package com.bekhruz.weatherforecast.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocationData
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.SixteenDayData
import com.bekhruz.weatherforecast.domain.models.currentweather.CurrentWeatherData
import com.bekhruz.weatherforecast.domain.models.geocoding.LocationResult
import com.bekhruz.weatherforecast.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import com.bekhruz.weatherforecast.core.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor() : BaseViewModel() {
    //useCases
    @Inject lateinit var getDeviceLocationWeatherUseCase: GetDeviceLocationWeatherUseCase
    @Inject lateinit var getSearchedLocationWeatherUseCase: GetSearchedLocationWeatherUseCase
    private val _sixteenDayWeatherData = MutableLiveData<SixteenDayData>()
    val sixteenDayWeatherData: LiveData<SixteenDayData> = _sixteenDayWeatherData
    private val _searchedLocation = MutableLiveData<SearchedLocationData>()
    private val _currentWeatherData = MutableLiveData<CurrentWeatherData>()
    val currentWeatherData: LiveData<CurrentWeatherData> = _currentWeatherData
    private var showingDeviceLocationWeather: Boolean = true

    fun getSearchedLocationData(searchedLocation: String): LiveData<SearchedLocationData> {
        vmScope.loadingLaunch {
            val response = getSearchedLocationWeatherUseCase.getSearchedLocationResults(searchedLocation)
            _searchedLocation.postValue(response)
        }
        return _searchedLocation
    }

    fun applySelectedLocationWeatherData(selectedLocation: LocationResult){
        if(!showingDeviceLocationWeather){
            vmScope.loadingLaunch {
                val weatherData = getSearchedLocationWeatherUseCase(selectedLocation)
                val currentWeatherAtSelectedLocation = weatherData.first
                val sixteenDayWeatherAtSelectedLocation = weatherData.second
                _currentWeatherData.postValue(currentWeatherAtSelectedLocation)
                _sixteenDayWeatherData.postValue(sixteenDayWeatherAtSelectedLocation)
            }
        }
    }


    @SuppressLint("MissingPermission")
    fun applyDeviceLocationWeatherData() {
        if(showingDeviceLocationWeather){
            vmScope.loadingLaunch {
                val weatherData = getDeviceLocationWeatherUseCase()
                val currentWeatherAtDeviceLocation = weatherData.first
                val sixteenDayWeatherAtDeviceLocation = weatherData.second
                _currentWeatherData.postValue(currentWeatherAtDeviceLocation)
                _sixteenDayWeatherData.postValue(sixteenDayWeatherAtDeviceLocation)
            }
        }
        showingDeviceLocationWeather = false
    }
}