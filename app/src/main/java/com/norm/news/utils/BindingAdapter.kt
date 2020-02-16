package com.norm.news.utils

import android.os.Build
import android.text.Html
import android.view.View
import android.view.View.*
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.norm.news.R
import com.norm.news.network.ApiStatus
import com.norm.news.widget.HtmlTextView

/**
 * Created by KZYT on 16/01/2020.
 */
@BindingAdapter("loadingVisibility")
fun bindLoadingStatus(
  progress: ProgressBar,
  status: ApiStatus?
) {
  when (status) {
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
fun bindStatus(
  imageView: ImageView,
  status: ApiStatus?
) {
  when (status) {
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

@BindingAdapter("imageUrl")
fun bindImageFromUrl(
  imageView: ImageView,
  imgUrl: String?
) {
  imgUrl?.let {
    val imgUri = imgUrl.toUri()
        .buildUpon()
        .scheme("https")
        .build()
    Glide.with(imageView.context)
        .load(imgUri)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imageView)
  }
}

@BindingAdapter("htmlText")
fun setHtmlText(
  textView: HtmlTextView,
  htmlText: String
) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    textView.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
  } else {
    @Suppress("DEPRECATION")
    textView.text = Html.fromHtml(htmlText)
  }
}

@BindingAdapter(value = ["dialogTitle", "fileLink"], requireAll = true)
fun createDialogForFile(
  button: Button,
  dialogTitle: String,
  fileLink: String
) {
  val context = button.context
  button.setOnClickListener {
    val webView = WebView(context).apply { loadUrl(fileLink) }
    AlertDialog.Builder(context)
        .setTitle(dialogTitle)
        .setView(webView)
        .create()
        .show()
  }
}

@BindingAdapter("invisibleUnless")
fun invisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) VISIBLE else INVISIBLE
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) VISIBLE else GONE
}