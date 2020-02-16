package com.norm.news.repository

/**
 * Created by Kyaw Zayar Tun on 2020-01-17.
 */
interface NewsArticleRepository {
  suspend fun refreshArticles()

  suspend fun searchArticlesByTitle(): Set<String>
}