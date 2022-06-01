package com.bekhruz.weatherforecast.data.localdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "location_name")
    val locationName:String,
    @ColumnInfo(name = "latitude")
    val latitude:String,
    @ColumnInfo(name = "longitude")
    val longitude:String
)