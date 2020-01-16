package com.norm.news.ui.source

import androidx.recyclerview.widget.DiffUtil
import com.norm.news.network.model.NewsSource

/**
 * Created by KZYT on 16/01/2020.
 */
class NewsSourceDiffCallback: DiffUtil.ItemCallback<NewsSource>() {
    override fun areItemsTheSame(oldItem: NewsSource, newItem: NewsSource): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsSource, newItem: NewsSource): Boolean {
        return oldItem == newItem
    }
}