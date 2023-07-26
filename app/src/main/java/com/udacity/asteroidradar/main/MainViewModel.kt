package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaAPI
import com.udacity.asteroidradar.database.AsteroidDBHelper
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(val app: Application) : AndroidViewModel(app) {

    private val _picOfDay = MutableLiveData<PictureOfDay>()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    private val asteroidsRepository = AsteroidsRepository(AsteroidDBHelper.getInstance(app))

    val asteroids = asteroidsRepository.cachedAsteroids

    init {
        getPicture()
        getAsteroidsListFromRepository()
    }

    private fun getPicture() {
        //Suspend function should be called only from a coroutine or another suspend function
        viewModelScope.launch {
            _picOfDay.value = NasaAPI.retrofitService.getPictureOfDay()
        }
    }

    private fun getAsteroidsListFromRepository(){
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroidsList()
        }
    }

}