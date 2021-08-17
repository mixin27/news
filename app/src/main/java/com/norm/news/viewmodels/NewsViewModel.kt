package com.norm.news.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.norm.news.R
import com.norm.news.util.Constants.Companion.API_KEY
import com.norm.news.util.Constants.Companion.QUERY_API_KEY
import com.norm.news.util.Constants.Companion.QUERY_CATEGORY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    var networkStatus = false

    /**
     * queries preparation to apply on news api request
     */
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_CATEGORY] = "business"

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
        } else if (networkStatus) {
            Toast.makeText(
                getApplication(),
                R.string.back_online_message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}