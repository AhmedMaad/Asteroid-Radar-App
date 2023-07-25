package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaAPI
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDBHelper
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(val app: Application) : AndroidViewModel(app) {

    private val _picOfDay = MutableLiveData<PictureOfDay>()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    private val _asteroids = MutableLiveData<ArrayList<Asteroid>>()
    val asteroids: LiveData<ArrayList<Asteroid>>
        get() = _asteroids

    init {
        getPicture()
        getAsteroidsList()
    }

    private fun getPicture() {
        //Suspend function should be called only from a coroutine or another suspend function
        viewModelScope.launch {
            _picOfDay.value = NasaAPI.retrofitService.getPictureOfDay()
        }
    }

    private fun getAsteroidsList() {
        viewModelScope.launch {
            val sevenDays = getNextSevenDaysFormattedDates()
            //Log.d("trace", "${sevenDays}")
            val asteroidsString = NasaAPI.retrofitService.getAsteroids(sevenDays[0], sevenDays[6])
            _asteroids.value = parseAsteroidsJsonResult(JSONObject(asteroidsString))
            Log.d("trace", "${asteroids.value}")
            saveAsteroids()
        }
    }

    private fun saveAsteroids() {
        viewModelScope.launch {
            AsteroidDBHelper.getInstance(app).asteroidDao.saveAllAsteroids(asteroids.value!!)
        }
    }

}