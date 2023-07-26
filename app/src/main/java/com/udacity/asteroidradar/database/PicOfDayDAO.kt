package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.http.DELETE

@Dao
interface PicOfDayDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePicOfDay(picture: PictureOfDay)

    /*LiveData is already designed to handle asynchronous updates by automatically dispatching
     the updates on the main thread.
      Therefore, there is no need to mark LiveData functions as suspend.*/
    @Query("SELECT * FROM picture_of_day")
    fun getPicOfDay(): LiveData<PictureOfDay>

    @Query("DELETE FROM picture_of_day")
    suspend fun deleteCurrentPicture()

}