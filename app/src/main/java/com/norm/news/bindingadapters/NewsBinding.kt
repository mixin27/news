package com.norm.news.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.norm.news.data.database.entities.NewsEntity
import com.norm.news.models.News
import com.norm.news.util.NetworkResult

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class NewsBinding {
    companion object {

        @BindingAdapter("newsApiResponse", "readNewsDatabase", requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<News>?,
            database: List<NewsEntity>?
        ) {
            when (view) {
                is ImageView -> {
                    view.isVisible = apiResponse is NetworkResult.Error
                            && database.isNullOrEmpty()
                }
                is TextView -> {
                    view.isVisible = apiResponse is NetworkResult.Error
                            && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }

        @BindingAdapter("networkStatusVisibility")
        @JvmStatic
        fun handleNetworkStatusVisibility(view: MaterialCardView, status: Boolean) {
            view.visibility = when (status) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        }

    }
}