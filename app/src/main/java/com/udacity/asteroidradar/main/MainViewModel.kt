package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaAPI
import com.udacity.asteroidradar.database.AsteroidDBHelper
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(val app: Application) : AndroidViewModel(app) {
    
    private val asteroidsRepository = AsteroidsRepository(AsteroidDBHelper.getInstance(app))
    val asteroids = asteroidsRepository.cachedAsteroids
    val pictureOfDay = asteroidsRepository.cachedPictureOfDay

    init {
        getAsteroidsListFromRepository()
        getPictureOfDayFromRepository()
    }

    private fun getAsteroidsListFromRepository() {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroidsList()
        }
    }

    private fun getPictureOfDayFromRepository() {
        viewModelScope.launch {
            asteroidsRepository.refreshPicOfDay()
        }
    }

}