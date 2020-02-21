package com.norm.news.ui.search

/**
 * Created by KZYT on 15/02/2020.
 */
data class SearchResult(
//    val source: SourceInSearchResult,
    val id: String,
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String,
    val urlToImage: String? = "",
    val publishedAt: String,
    val content: String? = ""
)

data class SourceInSearchResult(
    val id: String,
    val name: String? = ""
)