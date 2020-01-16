package com.norm.news.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by KZYT on 16/01/2020.
 */
@Entity(tableName = "news_source_table")
data class SourceEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "country")
    val country: String
)