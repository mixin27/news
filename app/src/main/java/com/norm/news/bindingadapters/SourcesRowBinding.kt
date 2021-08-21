package com.norm.news.bindingadapters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.norm.news.models.topheadlines.Source

/**
 * Created by Kyaw Zayar Tun on 8/21/21.
 */
class SourcesRowBinding {
    companion object {
        @BindingAdapter("onSourceClickListener")
        @JvmStatic
        fun onSourceItemClick(sourcesRowLayout: ConstraintLayout, source: Source) {
            sourcesRowLayout.setOnClickListener {
                Log.d("onNewsClick", "called!")
                try {
                    val uri = Uri.parse(source.url)

                    val bundle = Bundle()
                    bundle.putBoolean("new_window", true)

                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.putExtras(bundle)
                    sourcesRowLayout.context.startActivity(intent)
                } catch (e: Exception) {
                    Log.d("onNewsClick", e.toString())
                }
            }
        }
    }
}