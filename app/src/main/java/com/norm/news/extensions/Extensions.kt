@file:JvmName("ExtensionUtils")

package com.norm.news.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by KZYT on 15/01/2020.
 */
fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachedToRoot: Boolean = false): View {
  return LayoutInflater.from(context)
      .inflate(layoutId, this, attachedToRoot)
}

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

/**
 * Truncate long text with a preference for word boundaries and without trailing punctuation.
 */
fun String.smartTruncate(length: Int): String {
  val words = split(" ")
  var added = 0
  var hasMore = false
  val builder = StringBuilder()
  for (word in words) {
    if (builder.length > length) {
      hasMore = true
      break
    }
    builder.append(word)
    builder.append(" ")
    added += 1
  }

  PUNCTUATION.map {
    if (builder.endsWith(it)) {
      builder.replace(builder.length - it.length, builder.length, "")
    }
  }

  if (hasMore) {
    builder.append("...")
  }
  return builder.toString()
}