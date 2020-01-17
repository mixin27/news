package com.norm.news.network.model

import android.os.Parcelable
import com.norm.news.database.entity.NewsArticleEntity
import com.norm.news.domain.NewsArticles
import kotlinx.android.parcel.Parcelize

/**
 * Created by KZYT on 16/01/2020.
 */
data class NewsResponse(
    val status: String,
    val totalResults: Long = 0,
    val articles: List<Article>
)

data class Article(
    val source: SourceInArticles,
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String,
    val urlToImage: String? = "",
    val publishedAt: String,
    val content: String? = ""
)

data class SourceInArticles(
    val id: String,
    val name: String? = ""
)

fun NewsResponse.adDomainModel(): List<NewsArticles> {
    return articles.map {
        NewsArticles(
            id = it.source.id,
            author = it.author ?: "",
            title = it.title ?: "",
            description = it.description ?: "",
            url = it.url,
            urlToImage = it.urlToImage ?: "",
            publishedAt = it.publishedAt,
            content = it.content ?: ""
        )
    }
}

fun NewsResponse.asDatabaseModel(): List<NewsArticleEntity> {
    return articles.map {
        NewsArticleEntity(
            id = it.source.id,
            author = it.author ?: "",
            title = it.title ?: "",
            description = it.description ?: "",
            url = it.url,
            urlToImage = it.urlToImage ?: "",
            publishedAt = it.publishedAt,
            content = it.content ?: ""
        )
    }
}