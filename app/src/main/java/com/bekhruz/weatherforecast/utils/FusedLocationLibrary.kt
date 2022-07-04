package com.bekhruz.weatherforecast.utils

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

interface FusedLocationLibrary {
    fun getFusedLocationProviderClient(): FusedLocationProviderClient
}

class FusedLocationLibraryImpl
    @Inject constructor( private val appContext: Context):FusedLocationLibrary{
    override fun getFusedLocationProviderClient(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }
}