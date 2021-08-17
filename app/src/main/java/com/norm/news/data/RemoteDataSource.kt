package com.norm.news.data

import com.norm.news.data.network.NewsApiService
import com.norm.news.models.News
import com.norm.news.models.topheadlines.Sources
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class RemoteDataSource @Inject constructor(
    private val newsApiService: NewsApiService
) {
    /**
     * Get top headlines news.
     */
    suspend fun getNews(queries: Map<String, String>): Response<News> {
        return newsApiService.getNews(queries)
    }

    /**
     * Search news.
     */
    suspend fun searchNews(searchQuery: Map<String, String>): Response<News> {
        return newsApiService.searchNews(searchQuery)
    }

    /**
     * Get all sources.
     */
    suspend fun getSources(queries: Map<String, String>): Response<Sources> {
        return newsApiService.getSources(queries)
    }
}