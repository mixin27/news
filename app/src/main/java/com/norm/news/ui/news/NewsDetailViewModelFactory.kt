package com.norm.news.ui.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.norm.news.domain.NewsArticles

/**
 * Created by Kyaw Zayar Tun on 2020-01-18.
 */
class NewsDetailViewModelFactory(
  private val application: Application,
  private val newsArticles: NewsArticles
) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return NewsDetailViewModel(application, newsArticles) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class $modelClass")
  }
}