package com.norm.news.utils

/**
 * Created by KZYT on 15/02/2020.
 */
object ColorUtils {
    fun parseHexColor(colorString: String): Int {
        if (colorString.isNotEmpty() && colorString[0] == '#') {
            // Use a long to avoid rollovers on #ffXXXXXX
            var color = java.lang.Long.parseLong(colorString.substring(1), 16)
            if (colorString.length == 7) {
                // Set the alpha value
                color = color or 0x00000000ff000000
            } else require(colorString.length == 9) { "Unknown color: $colorString" }
            return color.toInt()
        }
        throw IllegalArgumentException("Unknown color")
    }
}