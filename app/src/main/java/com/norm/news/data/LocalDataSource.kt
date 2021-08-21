package com.norm.news.data

import com.norm.news.data.database.NewsDao
import com.norm.news.data.database.entities.NewsEntity
import com.norm.news.data.database.entities.SourcesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class LocalDataSource @Inject constructor(
    private val newsDao: NewsDao
) {
    /**
     * Read all news.
     */
    fun readNews(): Flow<List<NewsEntity>> {
        return newsDao.readNews()
    }

    fun readSources(): Flow<List<SourcesEntity>> {
        return newsDao.readSources()
    }

    /**
     * Insert all news.
     */
    suspend fun insertNews(newsEntity: NewsEntity) {
        newsDao.insertNews(newsEntity)
    }

    suspend fun insertSources(sourcesEntity: SourcesEntity) {
        newsDao.insertSources(sourcesEntity)
    }
}