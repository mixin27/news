package com.norm.news.network.model

/**
 * Created by KZYT on 16/01/2020.
 */
data class NewsResponse(
    val status: String,
    val articles: List<Article>
)

data class Article(
    val id: String
)