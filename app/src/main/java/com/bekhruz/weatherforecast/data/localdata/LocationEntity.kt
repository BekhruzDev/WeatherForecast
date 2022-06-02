package com.bekhruz.weatherforecast.data.localdata

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bekhruz.weatherforecast.data.network.currentweather.CurrentForecast
import com.bekhruz.weatherforecast.data.network.geocoding.Location
import com.bekhruz.weatherforecast.data.network.sixteendayweather.SixteenDayForecast

@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @Embedded(prefix = "current_")
    val currentForecast: CurrentForecast,
    @Embedded(prefix = "sixteenday_")
    val sixteenDayForecast: SixteenDayForecast
)
