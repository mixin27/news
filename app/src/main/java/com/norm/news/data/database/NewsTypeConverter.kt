package com.norm.news.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.norm.news.models.News
import com.norm.news.models.topheadlines.Sources

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class NewsTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun newsToString(news: News): String {
        return gson.toJson(news)
    }

    @TypeConverter
    fun stringToNews(data: String): News {
        val listType = object : TypeToken<News>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun sourcesToString(sources: Sources): String {
        return gson.toJson(sources)
    }

    @TypeConverter
    fun stringToSources(data: String): Sources {
        val listType = object : TypeToken<Sources>() {}.type
        return gson.fromJson(data, listType)
    }
}