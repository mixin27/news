package com.norm.news.domain.search

import com.norm.news.domain.NewsArticles

/**
 * Created by Kyaw Zayar Tun on 2020-02-16.
 */
sealed class Searchable {
    class SearchedArticle(val articles: NewsArticles): Searchable()
}