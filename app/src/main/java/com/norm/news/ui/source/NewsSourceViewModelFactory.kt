package com.norm.news.ui.source

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.norm.news.data.local.dao.NewsSourceDao
import java.lang.IllegalArgumentException

/**
 * Created by KZYT on 16/01/2020.
 */
class NewsSourceViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsSourceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsSourceViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")
    }

}