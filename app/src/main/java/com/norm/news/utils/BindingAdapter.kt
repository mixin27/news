package com.norm.news.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.norm.news.R
import com.norm.news.network.ApiStatus

/**
 * Created by KZYT on 16/01/2020.
 */
@BindingAdapter("loadingVisibility")
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

@BindingAdapter("imgVisibility")
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