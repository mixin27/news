package com.norm.news.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.norm.news.models.topheadlines.Sources
import com.norm.news.util.Constants.Companion.SOURCES_TABLE

/**
 * Created by Kyaw Zayar Tun on 8/21/21.
 */
@Entity(tableName = SOURCES_TABLE)
class SourcesEntity(
    var sources: Sources
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}