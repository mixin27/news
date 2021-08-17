package com.norm.news.data.network

import com.norm.news.models.News
import com.norm.news.models.topheadlines.Sources
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
interface NewsApiService {

    @GET("/top-headlines")
    fun getNews(
        @QueryMap queries: Map<String, String>
    ): Response<News>

    @GET("/everything")
    fun searchNews(
        @QueryMap searchQuery: Map<String, String>
    ): Response<News>

    @GET("/top-headlines/sources")
    fun getSources(
        @QueryMap queries: Map<String, String>
    ): Response<Sources>
}