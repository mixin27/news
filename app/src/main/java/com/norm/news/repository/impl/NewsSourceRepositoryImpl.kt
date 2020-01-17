package com.norm.news.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.norm.news.database.NewsDatabase
import com.norm.news.database.entity.asDomainModel
import com.norm.news.domain.NewsSource
import com.norm.news.network.NewsApiService
import com.norm.news.network.model.asDatabaseModel
import com.norm.news.repository.NewsSourceRepository
import com.norm.news.utils.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class NewsSourceRepositoryImpl(
    private val database: NewsDatabase
): NewsSourceRepository {

    val sources: LiveData<List<NewsSource>> = Transformations.map(database.newsSourceDao.getAllSources()) {
        it.asDomainModel()
    }

    override suspend fun refreshNewsSource() {
        withContext(Dispatchers.IO) {
            Timber.d("Refresh news source is called")
            val newsSources = NewsApiService.retrofitService.getSourcesAsync().await()
            database.newsSourceDao.insertAll(newsSources.asDatabaseModel())
        }
    }
}