package com.bekhruz.weatherforecast.data.remotedata.repositories


import com.bekhruz.weatherforecast.data.remotedata.retrofitservice.SixteenDayForecastApi
import com.bekhruz.weatherforecast.data.remotedata.retrofitservice.CurrentWeatherApi
import com.bekhruz.weatherforecast.data.remotedata.retrofitservice.GeocodingApi
import com.bekhruz.weatherforecast.data.remotedata.dto.currentweatherdto.CurrentForecast
import com.bekhruz.weatherforecast.data.remotedata.dto.geocodingdto.Location
import com.bekhruz.weatherforecast.data.remotedata.dto.sixteendayweatherdto.SixteenDayForecast
import com.bekhruz.weatherforecast.data.remotedata.utils.Constants.API_KEY_CURRENT_WEATHER
import com.bekhruz.weatherforecast.data.remotedata.utils.Constants.API_KEY_GEOCODING
import com.bekhruz.weatherforecast.data.remotedata.utils.Constants.API_KEY_SIXTEEN_DAY_WEATHER
import retrofit2.Response
import javax.inject.Inject

interface WeatherRepository{
    suspend fun getCurrentWeather(latLon: String): Response<CurrentForecast>
    suspend fun getSixteenDayWeather(
        latitude: String,
        longitude: String
    ): Response<SixteenDayForecast>
    suspend fun getFullLocationInfo(location: String): Response<Location>

}
class WeatherRepositoryImpl @Inject constructor():WeatherRepository  {
    override suspend fun getCurrentWeather(latLon: String): Response<CurrentForecast> {
        return CurrentWeatherApi.retrofitService.getCurrentWeather(
            API_KEY_CURRENT_WEATHER,
            latLon,
            3,
            "no",
            "no"
        )
    }

    override suspend fun getSixteenDayWeather(
        latitude: String,
        longitude: String
    ): Response<SixteenDayForecast> {
        return SixteenDayForecastApi.retrofitService.getSixteenDayWeather(
            latitude,
            longitude,
            API_KEY_SIXTEEN_DAY_WEATHER
        )
    }

    override suspend fun getFullLocationInfo(location: String): Response<Location> {
        return GeocodingApi.retrofitService.getFullLocationInfo(
            location,
            "en",
            10,
            "city",
            "json",
            API_KEY_GEOCODING
        )
    }
}