package com.norm.news.ui.news

import android.app.Application
import androidx.lifecycle.ViewModel
import com.norm.news.domain.NewsArticles

/**
 * Created by Kyaw Zayar Tun on 2020-01-18.
 */
class NewsDetailViewModel(
  private val application: Application,
  newsArticles: NewsArticles
) : ViewModel() {
  val article = newsArticles
}