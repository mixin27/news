package com.norm.news.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.norm.news.models.News
import com.norm.news.util.Constants.Companion.NEWS_TABLE

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
@Entity(tableName = NEWS_TABLE)
class NewsEntity(
    var news: News
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}