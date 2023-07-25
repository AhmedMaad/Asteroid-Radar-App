package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDBHelper : RoomDatabase() {

    abstract val asteroidDao: AsteroidDAO

    companion object {
        //writes to this field are immediately made visible to other threads.
        @Volatile
        private var INSTANCE: AsteroidDBHelper? = null

        fun getInstance(context: Context): AsteroidDBHelper {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDBHelper::class.java,
                        "asteroid_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}