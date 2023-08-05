package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .client(
        OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
            .build()
    )
    .build()

//Retrofit has a built-in suspend support.
interface Callable {
    //Deferred value is a non-blocking cancellable future — it is a Job with a result.
    @GET("neo/rest/v1/feed?api_key=dePHzIKlQ9PTqPhVtnbaekIPrLKNjLKVMgwn8O9X")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): String

    @GET("planetary/apod?api_key=dePHzIKlQ9PTqPhVtnbaekIPrLKNjLKVMgwn8O9X")
    suspend fun getPictureOfDay(): PictureOfDay

}

//We will expose our retrofit service to the rest of the application
//using a public "object"
object NasaAPI {
    val retrofitService: Callable by lazy {
        retrofit.create(Callable::class.java)
    }
}