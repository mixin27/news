package com.norm.news.util

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class Constants {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "94127380fdfe4b268916724a8da21e63"

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
        const val QUERY_PAGE = "page"
        const val QUERY_SORT_BY = "sortBy"

        const val DEFAULT_CATEGORY = "business"
        const val DEFAULT_PAGE = "1"
        const val DEFAULT_SORT_BY = "popularity"

        // DataStore preferences
        const val PREFS_NAME = "prefs_news"
        const val PREFS_BACK_ONLINE = "back_online"
    }
}