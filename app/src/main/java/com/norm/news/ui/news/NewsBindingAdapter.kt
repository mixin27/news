package com.norm.news.ui.news

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norm.news.domain.NewsArticles

/**
 * Created by KZYT on 16/01/2020.
 */
@BindingAdapter("articleListData")
fun newsArticleItem(
  recyclerView: RecyclerView,
  data: List<NewsArticles>?
) {
  val adapter = recyclerView.adapter as ArticleAdapter
  adapter.submitList(data)
}