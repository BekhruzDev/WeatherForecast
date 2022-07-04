package com.bekhruz.weatherforecast.data.remote.repositories


import com.bekhruz.weatherforecast.data.remote.retrofitservice.SixteenDayForecastApi
import com.bekhruz.weatherforecast.data.remote.retrofitservice.CurrentWeatherApi
import com.bekhruz.weatherforecast.data.remote.retrofitservice.GeocodingApi
import com.bekhruz.weatherforecast.data.remote.dto.currentweatherdto.asDomain
import com.bekhruz.weatherforecast.data.remote.dto.geocodingdto.asDomain
import com.bekhruz.weatherforecast.data.remote.dto.sixteendayweatherdto.asDomain
import com.bekhruz.weatherforecast.data.remote.utils.Constants.API_KEY_CURRENT_WEATHER
import com.bekhruz.weatherforecast.data.remote.utils.Constants.API_KEY_GEOCODING
import com.bekhruz.weatherforecast.data.remote.utils.Constants.API_KEY_SIXTEEN_DAY_WEATHER
import com.bekhruz.weatherforecast.domain.models.SearchedLocation
import com.bekhruz.weatherforecast.domain.models.SixteenDay
import com.bekhruz.weatherforecast.domain.models.Weather
import javax.inject.Inject

interface WeatherRepository{
    suspend fun getCurrentWeather(latLon: String): Weather
    suspend fun getSixteenDayWeather(
        latitude: String,
        longitude: String
    ): SixteenDay
    suspend fun getFullLocationInfo(location: String): SearchedLocation

}
class WeatherRepositoryImpl @Inject constructor():WeatherRepository  {
    override suspend fun getCurrentWeather(latLon: String): Weather {
        return CurrentWeatherApi.retrofitService.getCurrentWeather(
            API_KEY_CURRENT_WEATHER,
            latLon,
            3,
            "no",
            "no"
        ).asDomain()
    }

    override suspend fun getSixteenDayWeather(
        latitude: String,
        longitude: String
    ): SixteenDay {
        return SixteenDayForecastApi.retrofitService.getSixteenDayWeather(
            latitude,
            longitude,
            API_KEY_SIXTEEN_DAY_WEATHER
        ).asDomain()
    }

    override suspend fun getFullLocationInfo(location: String): SearchedLocation {
        return GeocodingApi.retrofitService.getFullLocationInfo(
            location,
            "en",
            10,
            "city",
            "json",
            API_KEY_GEOCODING
        ).asDomain()
    }
}