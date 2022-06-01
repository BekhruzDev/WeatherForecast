package com.bekhruz.weatherforecast.data.localdata

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * from locationentity ORDER BY id DESC")
    fun getLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * from locationentity WHERE id = :id")
    fun getLocation(id:Int):Flow<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location:LocationEntity)

    @Update
    suspend fun update(location: LocationEntity)

    @Delete
    suspend fun delete(location: LocationEntity)
}