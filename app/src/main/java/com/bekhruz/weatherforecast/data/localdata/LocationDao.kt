package com.bekhruz.weatherforecast.data.localdata

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * from location ORDER BY id DESC")
    fun getLocations(): Flow<List<Location>>

    @Query("SELECT * from location WHERE id = :id")
    fun getLocation(id:Int):Flow<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location:Location)

    @Update
    suspend fun update(location: Location)

    @Delete
    suspend fun delete(location: Location)
}