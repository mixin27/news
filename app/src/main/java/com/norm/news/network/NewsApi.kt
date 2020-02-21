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

  @GET("everything")
  fun searchArticlesByQuery(
    /**
     * Keywords or phrases to search for in the article title and body.
     */
    @Query("q") query: String
  ): Deferred<NewsResponse>

  /**
   * Search everything.
   */
  @GET("everything")
  fun searchArticlesByQuery(
    /**
     * Keywords or phrases to search for in the article title and body.
     */
    @Query("q") query: String,

    /**
     * Keywords or phrases to search for in the article title only.
     *
     *  Advanced search is supported here:
     *  - Surround phrases with quotes (") for exact match.</li>
     *  - Prepend words or phrases that must appear with a + symbol. Eg: +bitcoin
     *  - Prepend words that must not appear with a - symbol. Eg: -bitcoin
     *  - Alternatively you can use the AND / OR / NOT keywords, and optionally group these with parenthesis.
     *  Eg: crypto AND (ethereum OR litecoin) NOT bitcoin.
     *
     *  The complete value for qInTitle must be URL-encoded.
     */
    @Query("qInTitle") qInTitle: String = "",

    /**
     * A comma-seperated string of identifiers (maximum 20) for the news sources or blogs you want headlines from.
     * Use the [/sources] endpoint to locate these programmatically or look at the [sources index].
     */
    @Query("sources") sources: String = "",

    /**
     * A comma-seperated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to restrict the search to.
     */
    @Query("domains") domains: String = "",

    /**
     * A comma-seperated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to remove from the results.
     */
    @Query("excludeDomains") excludeDomains: String = "",

    /**
     * A date and optional time for the oldest article allowed. This should be in ISO 8601 format
     * (e.g. 2020-02-15 or 2020-02-15T07:46:41) Default: the oldest according to your plan.
     */
    @Query("from") from: String = "",

    /**
     * A date and optional time for the newest article allowed. This should be in ISO 8601 format
     * (e.g. 2020-02-15 or 2020-02-15T07:46:41) Default: the newest according to your plan.
     */
    @Query("to") to: String = "",

    /**
     * The 2-letter ISO-639-1 code of the language you want to get headlines for.
     * Possible options: ar|de|en|es|fr|he|it|nl|no|pt|ru|se|ud|zh.
     * Default: all languages returned.
     */
    @Query("language") language: String = "",

    /**
     * Possible options: relevancy, popularity, publishedAt.
     */
    @Query("sortBy") sortBy: String = "",

    /**
     * The number of results to return per page. 20 is the default, 100 is the maximum.
     */
    @Query("pageSize") pageSize: Int = 100,

    /**
     * Use this to page through the results.
     * Developer pricing allowed is 1.
     */
    @Query("page") page: Int = 1
  ): Deferred<NewsResponse>

  /**
   * Search articles according to domain.
   * @param domains A comma-seperated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to restrict the search to.
   */
  @GET("everything")
  fun searchArticlesByDomains(
    /**
     * A comma-seperated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to restrict the search to.
     */
    @Query("domains") domains: String
  ): Deferred<NewsResponse>


  /**
   * Search articles according to [NewsSourceResponse.sources].
   * @param sources A comma-seperated string of identifiers (maximum 20) for the news sources or blogs you want headlines from.
   */
  @GET("everything")
  fun searchArticlesBySources(
    /**
     * A comma-seperated string of identifiers (maximum 20) for the news sources or blogs you want headlines from.
     * Use the [/sources] endpoint to locate these programmatically or look at the [sources index].
     */
    @Query("sources") sources: String
  ): Deferred<NewsResponse>
}