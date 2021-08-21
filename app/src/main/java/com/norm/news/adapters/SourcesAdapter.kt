package com.norm.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.norm.news.databinding.SourcesRowLayoutBinding
import com.norm.news.models.topheadlines.Source
import com.norm.news.models.topheadlines.Sources
import com.norm.news.util.MyDiffUtil

/**
 * Created by Kyaw Zayar Tun on 8/21/21.
 */
class SourcesAdapter : RecyclerView.Adapter<SourcesAdapter.MyViewHolder>() {

    private var sources = emptyList<Source>()

    class MyViewHolder(
        private val binding: SourcesRowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source) {
            binding.source = source
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SourcesRowLayoutBinding.inflate(inflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSource = sources[position]
        holder.bind(currentSource)
    }

    override fun getItemCount(): Int {
        return sources.size
    }

    fun setData(newSources: Sources) {
        val sourceDiffUtil = MyDiffUtil(sources, newSources.sources)
        val diffUtilResult = DiffUtil.calculateDiff(sourceDiffUtil)
        sources = newSources.sources
        diffUtilResult.dispatchUpdatesTo(this)
    }
}