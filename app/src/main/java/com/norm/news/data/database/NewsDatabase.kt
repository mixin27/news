package com.norm.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.norm.news.data.database.entities.NewsEntity

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}