package com.norm.news.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.norm.news.R

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class NewsRowBinding {
    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
                error(R.drawable.error_placeholder_image)
            }
        }
    }
}