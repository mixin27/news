package com.norm.news.ui.news

import androidx.recyclerview.widget.DiffUtil
import com.norm.news.network.model.Article

/**
 * Created by KZYT on 16/01/2020.
 */
class ArticleDiffCallback: DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}