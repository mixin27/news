package com.norm.news.bindingadapters

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.norm.news.R
import com.norm.news.models.Article
import com.norm.news.ui.fragments.news.NewsFragmentDirections
import com.norm.news.util.toFormattedDateString

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class NewsRowBinding {
    companion object {

        @BindingAdapter("onNewsClickListener")
        @JvmStatic
        fun onNewsClick(newsRowLayout: ConstraintLayout, article: Article) {
            newsRowLayout.setOnClickListener {
                Log.d("onNewsClick", "called!")
                try {
                    val action =
                        NewsFragmentDirections.actionNewsFragmentToNewsDetailsActivity(article)
                    newsRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onNewsClick", e.toString())
                }
            }
        }

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