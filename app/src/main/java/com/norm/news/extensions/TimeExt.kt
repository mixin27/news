@file:JvmName("TimeUtils")

package com.norm.news.extensions

import org.threeten.bp.ZonedDateTime
import java.util.*

fun String.toZoneDateTime(): ZonedDateTime {
    return ZonedDateTime.parse(this)
}

fun ZonedDateTime.toMilliSecond(): Long {
    return this.toEpochSecond()
}

/**
 * Code from [https://github.com/juanchosaravia/KedditBySteps/blob/master/app/src/main/java/com/droidcba/kedditbysteps/commons/extensions/TimeExt.kt]
 * Modified some text to be shorter
 */
fun Long.getFriendlyTime(): String {
    val dateTime = Date(this * 1000)
    val sb = StringBuffer()
    val current = Calendar.getInstance().time
    var diffInSeconds = ((current.time - dateTime.time) / 1000).toInt()

    val sec = if (diffInSeconds >= 60) (diffInSeconds % 60) else diffInSeconds
    diffInSeconds /= 60
    val min = if (diffInSeconds >= 60) (diffInSeconds % 60) else diffInSeconds
    diffInSeconds /= 60
    val hrs = if (diffInSeconds >= 24) (diffInSeconds % 24) else diffInSeconds
    diffInSeconds /= 24
    val days = if (diffInSeconds >= 30) (diffInSeconds % 30) else diffInSeconds
    diffInSeconds /= 30
    val months = if (diffInSeconds >= 12) (diffInSeconds % 12) else diffInSeconds
    diffInSeconds /= 12
    val years = diffInSeconds

    if (years > 0) {
        if (years == 1) {
            sb.append("1 yr")
        } else {
            sb.append("$years yrs")
        }
        if (years <= 6 && months > 0) {
            if (months == 1) {
                sb.append(" 1 mth")
            } else {
                sb.append(" $months months")
            }
        }
    } else if (months > 0) {
        if (months == 1) {
            sb.append("1 mth")
        } else {
            sb.append("$months mths")
        }
        if (months <= 6 && days > 0) {
            if (days == 1) {
                sb.append(" 1 day")
            } else {
                sb.append(" $days days")
            }
        }
    } else if (days > 0) {
        if (days == 1) {
            sb.append("1 day")
        } else {
            sb.append("$days days")
        }
        if (days <= 3 && hrs > 0) {
            if (hrs == 1) {
                sb.append(" 1 hr")
            } else {
                sb.append(" $hrs hrs")
            }
        }
    } else if (hrs > 0) {
        if (hrs == 1) {
            sb.append("1 hr")
        } else {
            sb.append("$hrs hrs")
        }
        if (min > 1) {
            sb.append(" $min mins")
        }
    } else if (min > 0) {
        if (min == 1) {
            sb.append("1 min")
        } else {
            sb.append("$min mins")
        }
        if (sec > 1) {
            sb.append(" $sec secs")
        }
    } else {
        if (sec <= 1) {
            sb.append("1 sec")
        } else {
            sb.append("$sec secs")
        }
    }

    sb.append(" ago")

    return sb.toString()
}