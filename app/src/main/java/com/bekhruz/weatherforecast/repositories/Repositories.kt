package com.bekhruz.weatherforecast.repositories

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.bekhruz.weatherforecast.network.WeatherApi
import com.bekhruz.weatherforecast.network.sevenday.SevenDayForecast
import com.bekhruz.weatherforecast.utils.Constants.API_KEY
import retrofit2.Response

object Repositories {
suspend fun getSevenDayWeather(location:String):Response<SevenDayForecast>{
    return WeatherApi.retrofitService.getSevenDayWeather(
        API_KEY,
        location,
    )
}
    /*fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestcode
            )
            false
        } else {
            true
        }
    }*/
}