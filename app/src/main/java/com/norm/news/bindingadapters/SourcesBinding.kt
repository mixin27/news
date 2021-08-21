package com.norm.news.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.norm.news.data.database.entities.SourcesEntity
import com.norm.news.models.topheadlines.Sources
import com.norm.news.util.NetworkResult

/**
 * Created by Kyaw Zayar Tun on 8/21/21.
 */
class SourcesBinding {
    companion object {
        @BindingAdapter("sourcesApiResponse", "readSourcesDatabase", requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<Sources>?,
            database: List<SourcesEntity>?
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
    }
}