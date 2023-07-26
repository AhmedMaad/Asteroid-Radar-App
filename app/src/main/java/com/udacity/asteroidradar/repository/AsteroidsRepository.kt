package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.NasaAPI
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val database: AsteroidDBHelper) {

    val cachedAsteroids = database.asteroidDao.getFilteredAsteroids()

    /*
    * Disk I/O, or reading and writing to disk, is slow and always blocks the current thread until the
    * operation is complete. Because of this, you have to run the disk I/O in the I/O dispatcher
    * designed to offload blocking I/O tasks to a shared pool of threads
    * */
    suspend fun refreshAsteroidsList(){
        withContext(Dispatchers.IO){
            val sevenDays = getNextSevenDaysFormattedDates()
            val asteroidsString = NasaAPI.retrofitService.getAsteroids(sevenDays[0], sevenDays[6])
            val asteroids = parseAsteroidsJsonResult(JSONObject(asteroidsString))
            database.asteroidDao.saveAllAsteroids(asteroids)
        }
    }

}