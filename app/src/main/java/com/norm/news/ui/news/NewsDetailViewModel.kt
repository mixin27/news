package com.norm.news.ui.news

import android.app.Application
import androidx.lifecycle.ViewModel
import com.norm.news.domain.NewsArticles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by Kyaw Zayar Tun on 2020-01-18.
 */
class NewsDetailViewModel(
    private val application: Application,
    newsArticles: NewsArticles
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val article = newsArticles

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}