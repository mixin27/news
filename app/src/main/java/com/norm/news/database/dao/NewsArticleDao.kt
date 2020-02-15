package com.norm.news.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.norm.news.database.entity.NewsArticleEntity

/**
 * Created by KZYT on 16/01/2020.
 */
@Dao
interface NewsArticleDao {
  @Insert
  fun insert(article: NewsArticleEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(articles: List<NewsArticleEntity>)

  @Update
  fun update(article: NewsArticleEntity)

  @Query("SELECT * from news_article_table WHERE id = :id")
  fun get(id: Long): NewsArticleEntity?

  @Query("DELETE FROM news_article_table")
  fun clear()

  @Query("SELECT * FROM news_article_table ORDER BY publishAt DESC LIMIT 1")
  fun getArticle(): NewsArticleEntity?

  @Query("SELECT * FROM news_article_table ORDER BY publishAt DESC")
  fun getAllArticles(): LiveData<List<NewsArticleEntity>>

  @Query("SELECT * FROM news_article_table WHERE id IN (:id) ORDER BY publishAt DESC")
  fun getAllArticlesBySource(id: String): LiveData<List<NewsArticleEntity>>

  @Query("SELECT * FROM news_article_table WHERE title LIKE :query")
  fun searchByTitle(query: String): MediatorLiveData<List<NewsArticleEntity>>
}