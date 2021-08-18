package com.norm.news.util

import com.norm.news.BuildConfig

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class Constants {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = BuildConfig.API_KEY

        // Database
        const val DB_NAME = "news_db"
        const val NEWS_TABLE = "news"

        // API queries
        const val QUERY_API_KEY = "apikey"
        const val QUERY_Q = "q"
        const val QUERY_Q_IN_TITLE = "qInTitle"
        const val QUERY_COUNTRY = "country"
        const val QUERY_TO = "to"
        const val QUERY_FROM = "from"
        const val QUERY_LANGUAGE = "language"
        const val QUERY_CATEGORY = "category"
        const val QUERY_SORT_BY = "sortBy"

        const val DEFAULT_CATEGORY = "business"
        const val DEFAULT_SORT_BY = "popularity"

        // DataStore preferences
        const val PREFS_NAME = "prefs_news"
        const val PREFS_BACK_ONLINE = "back_online"
        const val PREFS_CATEGORY = "category"
        const val PREFS_CATEGORY_ID = "category_id"
        const val PREFS_SORT_BY = "sortBy"
        const val PREFS_SORT_BY_ID = "sortBy_id"
    }
}