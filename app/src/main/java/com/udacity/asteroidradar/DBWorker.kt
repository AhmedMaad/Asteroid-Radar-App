package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDBHelper
import com.udacity.asteroidradar.repository.AsteroidsRepository

class DBWorker(val context: Context, workParams: WorkerParameters) :
    CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        val repo = AsteroidsRepository(AsteroidDBHelper.getInstance(context))
        repo.refreshPicOfDay()
        repo.refreshAsteroidsList()
        return Result.success()
    }

}