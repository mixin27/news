package com.norm.news.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by KZYT on 15/01/2020.
 */
fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachedToRoot: Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachedToRoot)
}