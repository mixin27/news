package com.norm.news.ui.news

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norm.news.database.NewsDatabase
import com.norm.news.domain.NewsArticles
import com.norm.news.network.ApiStatus
import com.norm.news.network.model.Article
import com.norm.news.repository.impl.NewsArticlesRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by KZYT on 16/01/2020.
 */
class NewsViewModel(
  application: Application,
  sourceId: String
) : ViewModel() {
  val newsArticleRepository =
    NewsArticlesRepositoryImpl(NewsDatabase.getInstance(application), sourceId)

  private var viewModelJob = Job()
  private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

  val articles = newsArticleRepository.articles

  private val _status = MutableLiveData<ApiStatus>()
  val status: LiveData<ApiStatus>
    get() = _status

  private val _navigateToSelectedItem = MutableLiveData<NewsArticles>()
  val navigateToSelectedItem: LiveData<NewsArticles>
    get() = _navigateToSelectedItem

  init {
    getArticlesFromNetwork()
  }

  private fun getArticlesFromNetwork() {
    coroutineScope.launch {
      try {
        _status.value = ApiStatus.LOADING
        newsArticleRepository.refreshArticles()
        _status.value = ApiStatus.SUCCESS
        // _eventNetworkError.value = false
        // _isNetworkErrorShown.value = false
      } catch (e: IOException) {
        if (articles.value!!.isEmpty()) {
          _status.value = ApiStatus.ERROR
          // _eventNetworkError.value = true
        } else {
          _status.value = ApiStatus.SUCCESS
        }
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelJob.cancel()
  }

  /**
   * When the property is clicked, set the [_navigateToSelectedItem] [MutableLiveData]
   * @param article The [Article] that was clicked on.
   */
  fun displayNewsSourceDetails(article: NewsArticles) {
    _navigateToSelectedItem.value = article
  }

  /**
   * After the navigation has taken place, make sure navigateToSelectedItem is set to null
   */
  fun displayNewsSourceDetailsComplete() {
    _navigateToSelectedItem.value = null
  }
}