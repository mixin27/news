package com.norm.news.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.norm.news.models.News
import com.norm.news.util.NetworkResult

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class NewsBinding {
    companion object {

        @BindingAdapter("newsApiResponse")
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<News>?
        ) {
            when (view) {
                is ImageView -> {
                    view.isVisible = apiResponse is NetworkResult.Error
                }
                is TextView -> {
                    view.isVisible = apiResponse is NetworkResult.Error
                    view.text = apiResponse?.message.toString()
                }
            }
        }

    }
}