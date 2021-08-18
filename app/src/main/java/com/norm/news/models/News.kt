package com.norm.news.models

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    val articles: List<Article>,

    @SerializedName("totalResults")
    val total: Int
)