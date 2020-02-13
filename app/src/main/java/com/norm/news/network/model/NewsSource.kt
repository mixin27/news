package com.norm.news.network.model

import com.norm.news.database.entity.SourceEntity

/**
 * Created by KZYT on 16/01/2020.
 */
data class NewsSourceResponse(
  val status: String,
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

/**
 * Convert Network results to database objects
 */
fun NewsSourceResponse.asDomainModel(): List<com.norm.news.domain.NewsSource> {
  return sources.map {
    com.norm.news.domain.NewsSource(
        id = it.id,
        name = it.name,
        description = it.description,
        url = it.url,
        category = it.category,
        language = it.language,
        country = it.country
    )
  }
}

/**
 * Convert Network results to database objects
 */
fun NewsSourceResponse.asDatabaseModel(): List<SourceEntity> {
  return sources.map {
    SourceEntity(
        id = it.id,
        name = it.name,
        description = it.description,
        url = it.url,
        category = it.category,
        language = it.language,
        country = it.country
    )
  }
}