package com.bekhruz.weatherforecast.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "hourly_time_epoch")
    val hourlyTimeEpoch:String,
    @ColumnInfo(name = "latitude")
    val latitude:String,
    @ColumnInfo(name = "longitude")
    val longitude:String

)
