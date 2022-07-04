package com.bekhruz.weatherforecast.domain.usecases

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.bekhruz.weatherforecast.presentation.viewmodels.WeatherViewModel
import com.bekhruz.weatherforecast.utils.FusedLocationLibrary
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

interface GetDeviceLocationUseCase {
    operator fun invoke(context: Context, activity: Activity)
   :Task<Location>
}
class GetDeviceLocationUseCaseImpl
@Inject constructor(
    private val fusedLocationProviderClient: FusedLocationLibrary,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getSixteenDayWeatherUseCase: GetSixteenDayWeatherUseCase
):GetDeviceLocationUseCase{
    override operator fun invoke(context: Context, activity: Activity
    ):Task<Location>{
        val task = fusedLocationProviderClient.getFusedLocationProviderClient().lastLocation
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
        }
        return task
/*
            task.addOnSuccessListener {
                val geoCoder = Geocoder(context, Locale.getDefault())
                if (it != null) {
                    val fullAddress = geoCoder.getFromLocation(it.latitude, it.longitude, 1)
                    val currentCityName = fullAddress[0].getAddressLine(0)
                    getCurrentWeatherUseCase("${it.latitude},${it.longitude}")
                    getSixteenDayWeatherUseCase(it.latitude.toString(), it.longitude.toString())
                }
            }*/
    }
}