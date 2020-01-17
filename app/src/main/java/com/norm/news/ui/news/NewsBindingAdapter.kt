package com.norm.news.ui.news

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.norm.news.R
import com.norm.news.network.model.Article

/**
 * Created by KZYT on 16/01/2020.
 */
@BindingAdapter("articleListData")
fun newsArticleItem(recyclerView: RecyclerView, data: List<Article>?) {
    val adapter = recyclerView.adapter as ArticleAdapter
    adapter.submitList(data)
}