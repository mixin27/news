package com.norm.news.ui.source

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norm.news.R
import com.norm.news.network.ApiStatus
import com.norm.news.network.model.NewsSource

/**
 * Created by KZYT on 16/01/2020.
 */
@BindingAdapter("listData")
fun newsSourceItems(recyclerView: RecyclerView, data: List<NewsSource>?) {
    val adapter = recyclerView.adapter as NewsSourceAdapter
    adapter.submitList(data)
}

@BindingAdapter("loadingStatus")
fun bindLoadingStatus(progress: ProgressBar, status: ApiStatus?) {
    when(status) {
        ApiStatus.LOADING -> {
            progress.visibility = View.VISIBLE
        }
        ApiStatus.SUCCESS -> {
            progress.visibility = View.GONE
        }
        ApiStatus.ERROR -> {
            progress.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(imageView: ImageView, status: ApiStatus?) {
    when(status) {
        ApiStatus.LOADING -> {
            imageView.visibility = View.GONE
        }
        ApiStatus.SUCCESS -> {
            imageView.visibility = View.GONE
        }
        ApiStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_connection_error)
        }
    }
}