package com.bekhruz.weatherforecast.viewmodels

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekhruz.weatherforecast.network.sevenday.SevenDayForecast
import com.bekhruz.weatherforecast.repositories.Repositories
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel : ViewModel() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val _weather = MutableLiveData<SevenDayForecast>()
    val weather: LiveData<SevenDayForecast> = _weather

    fun getWeatherData(location: String) {
        viewModelScope.launch {
            val response = Repositories.getSevenDayWeather(location)
            if (response.isSuccessful) {
                _weather.value = response.body()
            }
        }
    }
     fun getDeviceLocationData(context: Context,activity: Activity){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                2003)
            return
        }
        task.addOnSuccessListener{
            val geoCoder = Geocoder(context, Locale.getDefault())
            if (it != null){
                val fullAddress = geoCoder.getFromLocation(it.latitude,it.longitude,1)
                val currentCityName = fullAddress[0].getAddressLine(0)
                val currentStateName = fullAddress[0].getAddressLine(1)
                val currentCountryName = fullAddress[0].getAddressLine(2)
                getWeatherData(currentCityName)
                Log.d("HOME FRAGMENT","LOCATION IS $currentCityName, lat: ${it.latitude} and lon: ${it.longitude}")
            }
        }

    }
    fun getDate(epochSecond: Long): String {
        val date = Date(epochSecond * 1000)
        val dateFormat = SimpleDateFormat("EEEE | MMMM d", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(date)
    }
    fun getTime(epochSecond: Long):String{
        val time = Date(epochSecond * 1000)
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")
        return timeFormat.format(time)
    }
}