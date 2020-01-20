package com.norm.news.ui.source

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norm.news.database.NewsDatabase
import com.norm.news.domain.NewsSource
import com.norm.news.network.ApiStatus
import com.norm.news.repository.impl.NewsSourceRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

/**
 * Created by KZYT on 15/01/2020.
 */
class NewsSourceViewModel(
    application: Application
) : ViewModel() {

    private val newsSourceRepository =
        NewsSourceRepositoryImpl(NewsDatabase.getInstance(application))

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

    /**
     * News source lists to be displayed on the screen.
     */
    val sources = newsSourceRepository.sources

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
//    private var _eventNetworkError = MutableLiveData<Boolean>(false)
//    val eventNetworkError: LiveData<Boolean>
//        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
//    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
//    val isNetworkErrorShown: LiveData<Boolean>
//        get() = _isNetworkErrorShown

    private val _navigateToSelectedItem = MutableLiveData<NewsSource>()
    val navigateToSelectedItem: LiveData<NewsSource>
        get() = _navigateToSelectedItem

    init {
        getNewsSources()
    }

    private fun getNewsSources() {
        uiScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                newsSourceRepository.refreshNewsSource()
                _status.value = ApiStatus.SUCCESS
                // _eventNetworkError.value = false
                // _isNetworkErrorShown.value = false
            } catch (e: IOException) {
                if (sources.value!!.isEmpty()) {
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