package com.norm.news.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.norm.news.database.NewsDatabase
import com.norm.news.database.entity.asDomainModel
import com.norm.news.domain.NewsArticles
import com.norm.news.network.NewsApiService
import com.norm.news.network.model.asDatabaseModel
import com.norm.news.repository.NewsArticleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Created by Kyaw Zayar Tun on 2020-01-17.
 */
class NewsArticlesRepositoryImpl(
  private val database: NewsDatabase,
  private val sourceId: String = "",
  private val query: String = ""
) : NewsArticleRepository {

  val articles: LiveData<List<NewsArticles>> =
    Transformations.map(database.newsArticleDao.getAllArticlesBySource(sourceId)) {
      it.asDomainModel()
    }

  val allArticles: LiveData<List<NewsArticles>> =
    Transformations.map(database.newsArticleDao.getArticles()) {
      it.asDomainModel()
    }

  override suspend fun refreshArticles() {
    withContext(Dispatchers.IO) {
      Timber.d("Refresh news articles is called")
      val newsArticles = NewsApiService.retrofitService.getTopHeadLineArticlesAsync(sourceId)
          .await()
      database.newsArticleDao.insertAll(newsArticles.asDatabaseModel())
    }
  }
}