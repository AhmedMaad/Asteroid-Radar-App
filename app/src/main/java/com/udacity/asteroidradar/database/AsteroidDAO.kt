package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllAsteroids(asteroids: ArrayList<Asteroid>)

    /*LiveData is already designed to handle asynchronous updates by automatically dispatching
     the updates on the main thread.
      Therefore, there is no need to mark LiveData functions as suspend.*/
    @Query("SELECT * FROM asteroid ORDER BY close_approach_date")
    fun getFilteredAsteroids(): LiveData<List<Asteroid>>


}