package com.norm.news.domain

import com.norm.news.extensions.smartTruncate

/**
 * Domain model: [NewsSource]
 */
data class NewsSource(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
) {
    /**
     * Short description is used for displaying truncated descriptions in the UI
     */
    val shortDescription: String
        get() = description.smartTruncate(200)
}

/**
 * Domain model: [NewsArticles]
 */
data class NewsArticles(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)