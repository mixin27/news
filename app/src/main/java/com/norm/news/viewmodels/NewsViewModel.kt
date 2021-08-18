package com.norm.news.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.norm.news.R
import com.norm.news.data.DataStoreRepository
import com.norm.news.util.Constants.Companion.API_KEY
import com.norm.news.util.Constants.Companion.DEFAULT_CATEGORY
import com.norm.news.util.Constants.Companion.DEFAULT_PAGE
import com.norm.news.util.Constants.Companion.DEFAULT_SORT_BY
import com.norm.news.util.Constants.Companion.QUERY_API_KEY
import com.norm.news.util.Constants.Companion.QUERY_CATEGORY
import com.norm.news.util.Constants.Companion.QUERY_PAGE
import com.norm.news.util.Constants.Companion.QUERY_Q
import com.norm.news.util.Constants.Companion.QUERY_Q_IN_TITLE
import com.norm.news.util.Constants.Companion.QUERY_SORT_BY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    var networkStatus = false
    var backOnline = false

    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    private fun saveBackOnline(status: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(status)
        }

    /**
     * queries preparation to apply on news api request
     */
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_CATEGORY] = DEFAULT_CATEGORY
        queries[QUERY_PAGE] = DEFAULT_PAGE
        queries[QUERY_SORT_BY] = DEFAULT_SORT_BY

        return queries
    }

    /**
     * queries preparation to search
     */
    fun applySearchQueries(searchString: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_Q_IN_TITLE] = searchString
        queries[QUERY_Q] = searchString
        queries[QUERY_SORT_BY] = "relevancy"

        return queries
    }

    /**
     * Show toast message of network status.
     */
    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(
                getApplication(),
                R.string.no_internet_connection,
                Toast.LENGTH_SHORT
            ).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(
                    getApplication(),
                    R.string.back_online_message,
                    Toast.LENGTH_SHORT
                ).show()
                saveBackOnline(false)
            }
        }
    }

}