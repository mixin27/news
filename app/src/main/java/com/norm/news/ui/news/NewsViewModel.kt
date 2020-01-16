package com.norm.news.ui.news

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by KZYT on 16/01/2020.
 */
class NewsViewModel(
    application: Application,
    newsSourceId: String
): ViewModel() {

    val sourceId = newsSourceId

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}