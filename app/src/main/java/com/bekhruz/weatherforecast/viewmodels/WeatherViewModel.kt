package com.bekhruz.weatherforecast.viewmodels

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import com.bekhruz.weatherforecast.data.network.currentweather.CurrentForecast
import com.bekhruz.weatherforecast.repositories.Repositories
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.bekhruz.weatherforecast.application.WeatherForecastApplication
import com.bekhruz.weatherforecast.data.network.geocoding.Location
import com.bekhruz.weatherforecast.data.network.sixteendayweather.SixteenDayForecast
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel(application: Application) : AndroidViewModel(application){
    private val repositories = Repositories((application as WeatherForecastApplication).database.locationDao())
    private val allData = repositories.databaseItems
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val _currentWeatherData = MutableLiveData<CurrentForecast>(
        allData.value?.get(0)?.currentForecast
    )
    val currentWeatherData: LiveData<CurrentForecast> = _currentWeatherData

    private val _sixteenDayWeatherData = MutableLiveData<SixteenDayForecast>(
        allData.value?.get(0)?.sixteenDayForecast
    )
    val sixteenDayWeatherData:LiveData<SixteenDayForecast> = _sixteenDayWeatherData

    private val _searchedLocation = MutableLiveData<Location>()
    val searchedLocation:LiveData<Location> = _searchedLocation

    fun getRemoteDataIntoDatabase(latLon: String, latitude: String,
                                  longitude: String){
        viewModelScope.launch {
            repositories.fetchRemoteDataIntoDatabase(latLon, latitude, longitude)
        }
    }


    fun getSearchedLocationInfo(searchedLocation:String):LiveData<Location>{
        viewModelScope.launch {
            val response = repositories.getFullLocationInfo(searchedLocation)
            if (response.isSuccessful){
                _searchedLocation.value = response.body()
            }
        }
        return _searchedLocation
    }


    fun getIconsOfSixteenDayData(iconId:String):String{
        return String.format("https://www.weatherbit.io/static/img/icons/$iconId.png")
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
                getRemoteDataIntoDatabase("${it.latitude},${it.longitude}",
                    it.latitude.toString(),
                    it.longitude.toString())
                Log.d(TAG,"LOCATION IS $currentCityName, lat: ${it.latitude} and lon: ${it.longitude}")
            }
        }
    }

    //TODO: CONVERT THE TYPE INTO A CONSTANT
    fun getTime(epochSecond: Long, type:String):String{
        val time = Date(epochSecond * 1000)
        val timeFormat = when (type) {
            "date" -> SimpleDateFormat("EEEE | MMMM d", Locale.getDefault())
            "time" -> SimpleDateFormat("HH:mm", Locale.getDefault())
            else -> SimpleDateFormat("MMMM d", Locale.getDefault())
        }
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")
        return timeFormat.format(time)
    }

    companion object {
        private const val TAG = "WEATHER VIEW MODEL"
    }
}


/*private fun getCurrentWeather(latLon: String) {
    viewModelScope.launch {
        val response = Repositories.getCurrentWeather(latLon)
        if (response.isSuccessful) {
            _currentWeatherData.value = response.body()
        }
    }
}
private fun getSixteenDayWeather(latitude:String, longitude:String){
    viewModelScope.launch {
        val response = Repositories.getSixteenDayWeather(latitude, longitude)
        if (response.isSuccessful){
            _sixteenDayWeatherData.value = response.body()
        }
    }
}*/

/**
 * Factory for constructing WeatherViewModel with parameter
 */
class Factory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}