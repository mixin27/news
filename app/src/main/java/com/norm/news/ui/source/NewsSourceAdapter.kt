package com.norm.news.ui.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norm.news.databinding.ListItemSourceBinding
import com.norm.news.domain.NewsSource

/**
 * Created by KZYT on 16/01/2020.
 */
class NewsSourceAdapter(
  private val onClickListener: OnClickListener
) : ListAdapter<NewsSource, NewsSourceAdapter.SourceViewHolder>(NewsSourceDiffCallback()) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): SourceViewHolder {
    return SourceViewHolder.from(parent)
  }

  override fun onBindViewHolder(
    holder: SourceViewHolder,
    position: Int
  ) {
    val item = getItem(position)
    holder.itemView.setOnClickListener {
      onClickListener.onClick(item)
    }
    holder.bind(item)
  }

  class SourceViewHolder private constructor(private val binding: ListItemSourceBinding) :
      RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NewsSource) {
      binding.source = item
      binding.executePendingBindings()
    }

    companion object {
      fun from(parent: ViewGroup): SourceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemSourceBinding.inflate(layoutInflater, parent, false)
        return SourceViewHolder(binding)
      }
    }
  }

  /**
   * Custom listener that handles clicks on [RecyclerView] items.  Passes the [NewsSource]
   * associated with the current item to the [onClick] function.
   * @param clickListener lambda that will be called with the current [NewsSource]
   */
  class OnClickListener(val clickListener: (newsSource: NewsSource) -> Unit) {
    fun onClick(newsSource: NewsSource) = clickListener(newsSource)
  }
}