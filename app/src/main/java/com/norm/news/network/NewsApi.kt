package com.norm.news.network

import com.norm.news.network.model.NewsResponse
import com.norm.news.network.model.NewsSourceResponse
import com.norm.news.utils.API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by KZYT on 16/01/2020.
 */
interface NewsApi {
    @GET("sources")
    fun getSourcesAsync(
//        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<NewsSourceResponse>

    @GET("top-headlines")
    fun getTopHeadLineArticlesAsync(
        @Query("sources") source: String
//        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<NewsResponse>
}