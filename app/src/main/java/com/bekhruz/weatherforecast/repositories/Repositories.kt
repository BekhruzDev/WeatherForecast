package com.bekhruz.weatherforecast.repositories


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.bekhruz.weatherforecast.data.localdata.LocationDao
import com.bekhruz.weatherforecast.data.localdata.LocationEntity
import com.bekhruz.weatherforecast.data.network.SixteenDayForecastApi
import com.bekhruz.weatherforecast.data.network.CurrentWeatherApi
import com.bekhruz.weatherforecast.data.network.GeocodingApi
import com.bekhruz.weatherforecast.data.network.currentweather.CurrentForecast
import com.bekhruz.weatherforecast.data.network.geocoding.Location
import com.bekhruz.weatherforecast.data.network.sixteendayweather.SixteenDayForecast
import com.bekhruz.weatherforecast.utils.Constants.API_KEY_CURRENT_WEATHER
import com.bekhruz.weatherforecast.utils.Constants.API_KEY_GEOCODING
import com.bekhruz.weatherforecast.utils.Constants.API_KEY_SIXTEEN_DAY_WEATHER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repositories(private val locationDao: LocationDao) {
    suspend fun getCurrentWeather(latLon: String): Response<CurrentForecast> {
        return CurrentWeatherApi.retrofitService.getCurrentWeather(
            API_KEY_CURRENT_WEATHER,
            latLon,
            3,
            "no",
            "no"
        )
    }

    suspend fun getSixteenDayWeather(
        latitude: String,
        longitude: String
    ): Response<SixteenDayForecast> {
        return SixteenDayForecastApi.retrofitService.getSixteenDayWeather(
            latitude,
            longitude,
            API_KEY_SIXTEEN_DAY_WEATHER
        )
    }

    suspend fun getFullLocationInfo(location: String): Response<Location> {
        return GeocodingApi.retrofitService.getFullLocationInfo(
            location,
            "en",
            10,
            "city",
            "json",
            API_KEY_GEOCODING
        )
    }

    suspend fun getAsDatabaseModel(latLon: String, latitude: String,
                                   longitude: String):LocationEntity{
        val currentForecast = getCurrentWeather(latLon)
        val sixteenDayForecast = getSixteenDayWeather(latitude,longitude)
        return LocationEntity(
            currentForecast = currentForecast.body()!!,
            sixteenDayForecast = sixteenDayForecast.body()!!
        )
    }
    suspend fun fetchRemoteDataIntoDatabase(
        latLon: String, latitude: String,
        longitude: String
    ) {
       withContext(Dispatchers.IO) {
            val networkDataAsDatabaseModel = getAsDatabaseModel(latLon, latitude, longitude)
            locationDao.insert(networkDataAsDatabaseModel)
        }
    }

    val databaseItems: LiveData<List<LocationEntity>> = locationDao.getLocations().asLiveData()
    fun getDatabaseItem(id:Int):LiveData<LocationEntity>{
       return locationDao.getLocation(id).asLiveData()
    }
}