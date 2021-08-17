package com.norm.news.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */

/** Extensions function for [LiveData] to observe once. */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}

fun String.toZoneDateTime(): ZonedDateTime {
    return ZonedDateTime.parse(this)
}

fun ZonedDateTime.toMilliSecond(): Long {
    return this.toInstant().toEpochMilli()
}

fun String.toFormattedDateString(pattern: String): String {
    val df = DateTimeFormatter.ofPattern(pattern)
    return this.toZoneDateTime().format(df)
}