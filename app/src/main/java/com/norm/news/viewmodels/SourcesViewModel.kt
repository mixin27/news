package com.norm.news.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.norm.news.BuildConfig.API_KEY
import com.norm.news.R
import com.norm.news.data.DataStoreRepository
import com.norm.news.util.Constants.Companion.QUERY_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Kyaw Zayar Tun on 8/21/21.
 */
@HiltViewModel
class SourcesViewModel @Inject constructor(
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

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_API_KEY] = API_KEY
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