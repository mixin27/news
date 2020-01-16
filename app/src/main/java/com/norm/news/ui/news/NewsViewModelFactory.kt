package com.norm.news.ui.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Kyaw Zayar Tun on 2020-01-16.
 */
class NewsViewModelFactory(
    private val application: Application,
    private val sourceId: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(application, sourceId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")
    }
}