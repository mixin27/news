package com.norm.news.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norm.news.database.NewsDatabase
import com.norm.news.domain.NewsArticles
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * Created by KZYT on 15/02/2020.
 */
class SearchViewModel(
    private val application: Application
) : ViewModel(), SearchResultHandler {

    val job = Job()
    val uiScope = CoroutineScope(job + Dispatchers.Main)

    val newsArticleDao = NewsDatabase.getInstance(application).newsArticleDao

    var searchUsingRoomFeatureEnabled: Boolean = true

    private val _navigateToArticleDetail = MutableLiveData<NewsArticles>()
    val navigateToArticleDetail: LiveData<NewsArticles> = _navigateToArticleDetail

    private val _searchResults = MediatorLiveData<List<SearchResult>>()
    val searchResults: LiveData<List<SearchResult>> = _searchResults

    private val _isEmpty = MediatorLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    override fun openSearchResult(searchResult: SearchResult) {
        val article = NewsArticles(
            searchResult.id,
            searchResult.author!!,
            searchResult.title!!,
            searchResult.description!!,
            searchResult.url,
            searchResult.urlToImage!!,
            searchResult.publishedAt,
            searchResult.content!!
        )
        _navigateToArticleDetail.value = article
    }

    fun onSearchQueryChange(newQuery: String) {
        if (newQuery.length < 2) {
            onQueryCleared()
            return
        }

        executeSearch(newQuery)
    }

    private fun executeSearch(query: String) {
        if (searchUsingRoomFeatureEnabled) {
            Timber.d("Searching for query using Room: $query")
            uiScope.launch {
                processSearchResult(query)
            }
        }
    }

    private suspend fun processSearchResult(query: String) {
        withContext(Dispatchers.IO) {
            val result = newsArticleDao.searchByTitle(query)
            _searchResults.value = result.value!!.map {
                SearchResult(
                    it.id,
                    it.author,
                    it.title,
                    it.description,
                    it.url,
                    it.urlToImage,
                    it.publishedAt,
                    it.content
                )
            }

            _isEmpty.value = searchResults.value.isNullOrEmpty()
        }
    }

    private fun onQueryCleared() {
        _searchResults.value = emptyList()
        // Explicitly set false to not show the "No results" state
        _isEmpty.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}