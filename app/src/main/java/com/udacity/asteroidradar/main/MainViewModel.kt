package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.AsteroidDBHelper
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(val app: Application) : AndroidViewModel(app) {

    private val asteroidsRepository = AsteroidsRepository(AsteroidDBHelper.getInstance(app))
    val asteroids = asteroidsRepository.cachedAsteroids
    val pictureOfDay = asteroidsRepository.cachedPictureOfDay

    private val _hasError = MutableLiveData(false)
    val hasError: LiveData<Boolean>
        get() = _hasError

    init {
        getAsteroidsListFromRepository()
        getPictureOfDayFromRepository()
    }

    fun getAsteroidsListFromRepository() {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshAsteroidsList()
            }
            catch (e: Exception) {
                _hasError.value = true
            }

        }
    }

     fun getPictureOfDayFromRepository() {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshPicOfDay()
            }
            catch (e: Exception) {
                _hasError.value = true
            }
        }
    }

}

