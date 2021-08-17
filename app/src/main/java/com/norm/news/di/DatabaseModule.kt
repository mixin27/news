package com.norm.news.di

import android.content.Context
import androidx.room.Room
import com.norm.news.data.database.NewsDatabase
import com.norm.news.util.Constants.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            DB_NAME
        ).build()

    @Singleton
    @Provides
    fun provideNewsDao(database: NewsDatabase) = database.newsDao()
}