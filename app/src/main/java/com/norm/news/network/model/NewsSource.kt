package com.norm.news.network.model

import androidx.room.Embedded

/**
 * Created by KZYT on 16/01/2020.
 */
data class NewsSourceResponse(
    val status: String,
    @Embedded
    val sources: List<NewsSource>
)

data class NewsSource(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)