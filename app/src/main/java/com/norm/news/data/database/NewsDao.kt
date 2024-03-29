package com.norm.news.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.norm.news.data.database.entities.NewsEntity
import com.norm.news.data.database.entities.SourcesEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: NewsEntity)

    @Query("SELECT * FROM news ORDER BY id ASC")
    fun readNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sourcesEntity: SourcesEntity)

    @Query("SELECT * FROM sources ORDER BY id ASC")
    fun readSources(): Flow<List<SourcesEntity>>
}