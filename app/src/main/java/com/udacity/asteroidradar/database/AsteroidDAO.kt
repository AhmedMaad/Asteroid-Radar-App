package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDAO {

    @Insert
    suspend fun saveAllAsteroids(asteroids: ArrayList<Asteroid>)



}