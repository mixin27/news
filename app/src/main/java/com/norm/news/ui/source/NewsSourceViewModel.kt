package com.norm.news.ui.source

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norm.news.data.local.dao.NewsSourceDao
import com.norm.news.network.ApiStatus
import com.norm.news.network.NewsApiService
import com.norm.news.network.model.NewsSource
import com.norm.news.network.model.NewsSourceResponse
import com.norm.news.utils.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

/**
 * Created by KZYT on 15/01/2020.
 */
class NewsSourceViewModel(
    application: Application,
    newsSourceDao: NewsSourceDao
) : ViewModel() {

    /**
     * Hold a reference to NewsDatabase via NewsSourceDao.
     */
    val database = newsSourceDao

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Data from database
    // val sources = database.getAllSources()

    private val _response = MutableLiveData<NewsSourceResponse>()
    val response: LiveData<NewsSourceResponse>
        get() = _response

    private val _sources = MutableLiveData<List<NewsSource>>()
    val sources: LiveData<List<NewsSource>>
        get() = _sources

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _navigateToSelectedItem = MutableLiveData<NewsSource>()
    val navigateToSelectedItem: LiveData<NewsSource>
        get() = _navigateToSelectedItem

    init {
        getNewsSources()
    }

    private fun getNewsSources() {
        uiScope.launch {
            val allSources = NewsApiService.retrofitService.getSourcesAsync(API_KEY)
            try {
                _status.value = ApiStatus.LOADING
                val listResult = allSources.await()
                _status.value = ApiStatus.SUCCESS
                _response.value = listResult
                _sources.value = listResult.sources
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Timber.i("Job cancel!")
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedItem] [MutableLiveData]
     * @param newsSource The [NewsSource] that was clicked on.
     */
    fun displayNewsSourceDetails(newsSource: NewsSource) {
        _navigateToSelectedItem.value = newsSource
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedItem is set to null
     */
    fun displayNewsSourceDetailsComplete() {
        _navigateToSelectedItem.value = null
    }

}