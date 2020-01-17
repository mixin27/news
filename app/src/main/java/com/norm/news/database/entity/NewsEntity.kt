package com.norm.news.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.norm.news.domain.NewsArticles

/**
 * Created by KZYT on 16/01/2020.
 */
@Entity(tableName = "news_article_table")
data class NewsArticleEntity(
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "author")
    val author: String? = "",

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "description")
    val description: String? = "",

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String? = "",

    @ColumnInfo(name = "publishAt")
    val publishedAt: String,

    @ColumnInfo(name = "content")
    val content: String? = ""
)

fun List<NewsArticleEntity>.asDomainModel(): List<NewsArticles> {
    return map {
        NewsArticles(
            id = it.id,
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