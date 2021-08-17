package com.norm.news.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.norm.news.R
import com.norm.news.util.toFormattedDateString

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class NewsRowBinding {
    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String?) {
            imageView.load(url) {
                crossfade(600)
                error(R.drawable.error_placeholder_image)
            }
        }

        @BindingAdapter("setPublishedAt")
        @JvmStatic
        fun setPublishedAtText(textView: TextView, publishedAt: String) {
            textView.text = publishedAt.toFormattedDateString("MMM d, yyyy h:mm a")
        }
    }
}