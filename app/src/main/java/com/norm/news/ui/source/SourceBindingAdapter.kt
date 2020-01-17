package com.norm.news.ui.source

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norm.news.domain.NewsSource

/**
 * Created by KZYT on 16/01/2020.
 */
@BindingAdapter("sourceListData")
fun newsSourceItems(recyclerView: RecyclerView, data: List<NewsSource>?) {
    val adapter = recyclerView.adapter as NewsSourceAdapter
    adapter.submitList(data)
}