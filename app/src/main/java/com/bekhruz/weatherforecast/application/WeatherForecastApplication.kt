package com.bekhruz.weatherforecast.application

import android.app.Application
import com.bekhruz.weatherforecast.data.localdata.LocationDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherForecastApplication : Application() {
    val database: LocationDatabase by lazy { LocationDatabase.getDatabase(this) }
}