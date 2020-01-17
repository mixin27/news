package com.norm.news.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.norm.news.database.NewsDatabase
import com.norm.news.repository.impl.NewsSourceRepositoryImpl
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by Kyaw Zayar Tun on 2020-01-17.
 */
class NewsSourceDataWorker(
    context: Context, params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val database = NewsDatabase.getInstance(applicationContext)
        val repository = NewsSourceRepositoryImpl(database)

        try {
            repository.refreshNewsSource()
            Timber.d("WorkManager: Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.norm.news.work.NewsSourceDataWorker"
    }
}