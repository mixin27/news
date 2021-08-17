package com.norm.news.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.norm.news.data.Repository
import com.norm.news.models.News
import com.norm.news.models.topheadlines.Sources
import com.norm.news.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /**
     * Local database
     */


    /**
     * Remote
     */
    val newsResponse: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val searchedNewsResponse: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val sourcesResponse: MutableLiveData<NetworkResult<Sources>> = MutableLiveData()

    fun getNews(queries: Map<String, String>) = viewModelScope.launch {
        getNewsSafeCall(queries)
    }

    fun searchedNews(searchQuery: Map<String, String>) =
        viewModelScope.launch {
            searchedNewsSafeCall(searchQuery)
        }

    fun getSources(queries: Map<String, String>) =
        viewModelScope.launch {
            getSourcesSafeCall(queries)
        }

    private suspend fun getNewsSafeCall(queries: Map<String, String>) {
        // set loading state
        newsResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getNews(queries)
                newsResponse.value = handleNewsResponse(response)

                // todo: offline cache
            } catch (e: Exception) {
                newsResponse.value = NetworkResult.Error("News not found.")
            }
        } else {
            newsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun searchedNewsSafeCall(searchQuery: Map<String, String>) {
        // set loading state
        searchedNewsResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchNews(searchQuery)
                searchedNewsResponse.value = handleNewsResponse(response)
            } catch (e: Exception) {
                searchedNewsResponse.value = NetworkResult.Error("News not found.")
            }
        } else {
            searchedNewsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun getSourcesSafeCall(queries: Map<String, String>) {
        // set loading state
        sourcesResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getSources(queries)
                sourcesResponse.value = handleSourcesResponse(response)
            } catch (e: Exception) {
                sourcesResponse.value = NetworkResult.Error("Sources not found.")
            }
        } else {
            sourcesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleNewsResponse(response: Response<News>): NetworkResult<News>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Connection Timeout.")
            }
            response.code() == 401 -> {
                return NetworkResult.Error("Unauthorized - May be API key was missing.")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.code() == 429 -> {
                return NetworkResult.Error("Tool many requests")
            }
            response.body()!!.articles.isNullOrEmpty() -> {
                return NetworkResult.Error("News not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleSourcesResponse(response: Response<Sources>): NetworkResult<Sources>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Connection Timeout.")
            }
            response.code() == 401 -> {
                return NetworkResult.Error("Unauthorized - May be API key was missing.")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.code() == 429 -> {
                return NetworkResult.Error("Tool many requests")
            }
            response.body()!!.sources.isNullOrEmpty() -> {
                return NetworkResult.Error("Sources not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    /**
     * Check internet connection.
     */
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}