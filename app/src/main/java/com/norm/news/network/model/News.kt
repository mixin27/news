package com.norm.news.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by KZYT on 16/01/2020.
 */
data class NewsResponse(
    val status: String,
    val articles: List<Article>
)


@Parcelize
data class Article(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) : Parcelable