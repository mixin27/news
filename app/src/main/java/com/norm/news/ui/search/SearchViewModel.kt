package com.norm.news.ui.search

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norm.news.database.NewsDatabase
import com.norm.news.domain.NewsArticles
import com.norm.news.domain.search.Searchable
import com.norm.news.repository.impl.NewsArticlesRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by KZYT on 15/02/2020.
 */
class SearchViewModel(
    private val application: Application
) : ViewModel(), SearchResultHandler {

    private val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

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

        // executeSearch(newQuery)
    }

    private fun executeSearch(query: String) {
        if (searchUsingRoomFeatureEnabled) {
            Timber.d("Searching for query using Room: $query")
            uiScope.launch {
                processSearchResult(query.trim())
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun processSearchResult(parameters: String) {
        uiScope.launch {
//            val query = if (parameters.isNotEmpty()) {
//                parameters.split(", ", " ", ",").take(5).map {
//                    "${it.toLowerCase()}*"
//                }.joinToString { " AND " }
//            } else {
//                parameters
//            }
//            val articleRepository = NewsArticlesRepositoryImpl(NewsDatabase.getInstance(application), query = query)
//            // val articlesResult = articleRepository.searchArticlesByTitle()
//            val allArticles = articleRepository.allArticles
//
//            val searchArticles = allArticles.value!!.filter {
//                it.url in articlesResult
//            }.map {
//                Searchable.SearchedArticle(it)
//            }
//
//            _searchResults.value = searchArticles.map {
//                SearchResult(
//                    it.articles.id,
//                    it.articles.author,
//                    it.articles.title,
//                    it.articles.description,
//                    it.articles.url,
//                    it.articles.urlToImage,
//                    it.articles.publishedAt,
//                    it.articles.content
//                )
//            }

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