package com.norm.news.ui.news

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norm.news.network.model.Article

/**
 * Created by KZYT on 16/01/2020.
 */
@BindingAdapter("listData")
fun newsItem(recyclerView: RecyclerView, data: List<Article>?) {
//    val adapter = recyclerView.adapter as NewsSourceAdapter
//    adapter.submitList(data)
}