package com.norm.news.network.model

import android.os.Parcelable
import com.norm.news.extensions.getFriendlyTime
import com.norm.news.extensions.toMilliSecond
import com.norm.news.extensions.toZoneDateTime
import kotlinx.android.parcel.Parcelize

/**
 * Created by KZYT on 16/01/2020.
 */
data class NewsResponse(
    val status: String,
    val totalResults: Long = 0,
    val articles: List<Article>
)


@Parcelize
data class Article(
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String,
    val urlToImage: String? = "",
    val publishedAt: String,
    val content: String? = ""
) : Parcelable {
    val getFriendlyDate
        get() = publishedAt.toZoneDateTime().toMilliSecond().getFriendlyTime()
}