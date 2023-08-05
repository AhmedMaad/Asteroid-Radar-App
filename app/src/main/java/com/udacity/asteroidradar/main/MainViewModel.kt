package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidDBHelper
import com.udacity.asteroidradar.database.AsteroidsDBFilter
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
        getAsteroidsListFromRepository(AsteroidsDBFilter.SHOW_ALL)
        getPictureOfDayFromRepository()
    }

    fun getAsteroidsListFromRepository(filter: AsteroidsDBFilter) {
        viewModelScope.launch {
            try {
                when (filter.value) {
                    Constants.ALL -> asteroidsRepository.refreshAsteroidsList()
                    Constants.WEEK -> asteroidsRepository.refreshAsteroidsList(startDate = 1)
                    Constants.TODAY -> asteroidsRepository.refreshAsteroidsList(
                        startDate = 0,
                        endDate = 0
                    )
                }

            } catch (e: Exception) {
                _hasError.value = true
            }

        }
    }

    fun getPictureOfDayFromRepository() {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshPicOfDay()
            } catch (e: Exception) {
                _hasError.value = true
            }
        }
    }

    fun updateFilter(filter: AsteroidsDBFilter) {
        getAsteroidsListFromRepository(filter)
    }

}

