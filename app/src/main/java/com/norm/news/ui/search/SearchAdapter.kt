package com.norm.news.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norm.news.databinding.ListItemSearchBinding

/**
 * Created by KZYT on 15/02/2020.
 */
class SearchAdapter(
    private val searchViewModel: SearchViewModel
) : ListAdapter<SearchResult, SearchViewHolder>(SearchDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ListItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            searchViewModel
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchViewHolder(
    private val binding: ListItemSearchBinding,
    private val searchViewModel: SearchViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(searchResult: SearchResult) {
        binding.eventListener = searchViewModel
        binding.searchResult = searchResult
        binding.executePendingBindings()
    }
}

object SearchDiff : DiffUtil.ItemCallback<SearchResult>() {
    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
        oldItem == newItem
}