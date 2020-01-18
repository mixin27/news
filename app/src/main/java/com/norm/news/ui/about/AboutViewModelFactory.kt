package com.norm.news.ui.about

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Kyaw Zayar Tun on 2020-01-18.
 */
class AboutViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AboutViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")
    }
}