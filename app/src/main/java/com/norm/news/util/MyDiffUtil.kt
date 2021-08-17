package com.norm.news.util

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
class MyDiffUtil<E>(
    private val oldList: List<E>,
    private val newList: List<E>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}