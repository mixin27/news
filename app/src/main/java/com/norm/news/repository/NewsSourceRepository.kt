package com.norm.news.repository

interface NewsSourceRepository {
    suspend fun refreshNewsSource()
}