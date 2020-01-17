package com.norm.news.ui.news

import androidx.recyclerview.widget.DiffUtil
import com.norm.news.domain.NewsArticles
import com.norm.news.network.model.Article

/**
 * Created by KZYT on 16/01/2020.
 */
class ArticleDiffCallback: DiffUtil.ItemCallback<NewsArticles>() {
    override fun areItemsTheSame(oldItem: NewsArticles, newItem: NewsArticles): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsArticles, newItem: NewsArticles): Boolean {
        return oldItem == newItem
    }
}