package com.norm.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.norm.news.database.dao.NewsArticleDao
import com.norm.news.database.dao.NewsSourceDao
import com.norm.news.database.entity.NewsArticleEntity
import com.norm.news.database.entity.SourceEntity

/**
 * Created by KZYT on 16/01/2020.
 */
@Database(
    entities = [SourceEntity::class, NewsArticleEntity::class], version = 4, exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
  abstract val newsSourceDao: NewsSourceDao
  abstract val newsArticleDao: NewsArticleDao

  companion object {
    @Volatile
    private var INSTANCE: NewsDatabase? = null

    fun getInstance(context: Context): NewsDatabase {
      synchronized(this) {
        var instance = INSTANCE
        if (instance == null) {
          instance = Room.databaseBuilder(
              context.applicationContext,
              NewsDatabase::class.java,
              "news_database"
          )
              .fallbackToDestructiveMigration()
              .build()

          INSTANCE = instance
        }
        return instance
      }
    }
  }
}