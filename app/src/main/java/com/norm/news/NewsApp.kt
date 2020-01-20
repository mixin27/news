package com.norm.news

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import androidx.work.*
import com.jakewharton.threetenabp.AndroidThreeTen
import com.norm.news.work.NewsSourceDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import leakcanary.AppWatcher
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by KZYT on 16/01/2020.
 */
class NewsApp: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        // Leakcanary
         if (BuildConfig.DEBUG) {
             AppWatcher.config = AppWatcher.config.copy(watchActivities = false)
         }

        // ThreetenABP
        AndroidThreeTen.init(this)

        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            // Timber
            Timber.plant(Timber.DebugTree())
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<NewsSourceDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraint)
            .build()
        Timber.d("WorkManager: Periodic Work request for sync is scheduled")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            NewsSourceDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}