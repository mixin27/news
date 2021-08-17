package com.norm.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.norm.news.databinding.NewsRowLayoutBinding
import com.norm.news.models.Article
import com.norm.news.models.News
import com.norm.news.util.MyDiffUtil

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var articles = emptyList<Article>()

    class MyViewHolder(private val binding: NewsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.article = article
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = NewsRowLayoutBinding.inflate(inflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentArticle = articles[position]
        holder.bind(currentArticle)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setData(newArticles: News) {
        val articlesDiffUtil = MyDiffUtil(articles, newArticles.articles)
        val diffUtilResult = DiffUtil.calculateDiff(articlesDiffUtil)
        articles = newArticles.articles
        diffUtilResult.dispatchUpdatesTo(this)
    }
}