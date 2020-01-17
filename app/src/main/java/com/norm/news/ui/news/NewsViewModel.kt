package com.norm.news.ui.news

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norm.news.network.ApiStatus
import com.norm.news.network.NewsApiService
import com.norm.news.network.model.Article
import com.norm.news.network.model.NewsResponse
import com.norm.news.utils.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by KZYT on 16/01/2020.
 */
class NewsViewModel(
    application: Application,
    newsSourceId: String
) : ViewModel() {

    val sourceId = newsSourceId

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _response = MutableLiveData<NewsResponse>()
    val response: LiveData<NewsResponse>
        get() = _response

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    private val _navigateToSelectedItem = MutableLiveData<Article>()
    val navigateToSelectedItem: LiveData<Article>
        get() = _navigateToSelectedItem

    init {
        getArticlesFromNetwork()
    }

    private fun getArticlesFromNetwork() {
        coroutineScope.launch {
            val newsResponse = NewsApiService.retrofitService.getTopHeadLineArticlesAsync(
                sourceId,
                API_KEY
            )
            try {
                _status.value = ApiStatus.LOADING
                val result = newsResponse.await()
                _status.value = ApiStatus.SUCCESS

                _response.value = result
                _articles.value = result.articles
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.i("Failed: ${_response.value}")
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
    fun displayNewsSourceDetails(article: Article) {
        _navigateToSelectedItem.value = article
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedItem is set to null
     */
    fun displayNewsSourceDetailsComplete() {
        _navigateToSelectedItem.value = null
    }
}