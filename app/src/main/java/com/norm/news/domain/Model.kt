package com.norm.news.domain

import android.os.Parcelable
import com.norm.news.extensions.*
import kotlinx.android.parcel.Parcelize

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
)

/**
 * Domain model: [NewsArticles]
 */
@Parcelize
data class NewsArticles(
    val id: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) : Parcelable {
    val getFriendlyDate
        get() = publishedAt.toZoneDateTime().toMilliSecond().getFriendlyTime()

    val getDateTime
        get() = publishedAt.toZoneDateTime().toMilliSecond().secondToDateTime()

    val getShorDescription
    get() = content.smartTruncate(50)
}