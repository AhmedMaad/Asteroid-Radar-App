package com.udacity.asteroidradar.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaAPI
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _picOfDay = MutableLiveData<PictureOfDay>()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    fun getPicture() {
        //Suspend function should be called only from a coroutine or another suspend function
        viewModelScope.launch {
            _picOfDay.value = NasaAPI.retrofitService.getPictureOfDay()
        }
    }

    /*private val _imageURL = MutableLiveData<String>()
    val imageURL: LiveData<String>
        get() = _imageURL

    fun getPicture() {
        //Suspend function should be called only from a coroutine or another suspend function
        viewModelScope.launch {
            val picOfDay = NasaAPI.retrofitService.getPictureOfDay()
            if (picOfDay.mediaType == "image") {
                val url = picOfDay.url
                _imageURL.value = url
            }

        }

    }*/

}