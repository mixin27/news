package com.norm.news.models.topheadlines


import com.google.gson.annotations.SerializedName

data class Sources(
    @SerializedName("sources")
    val sources: List<Source>
)