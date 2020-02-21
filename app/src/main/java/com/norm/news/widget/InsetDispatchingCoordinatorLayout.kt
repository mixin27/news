package com.norm.news.widget

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * A fixed version of [CoordinatorLayout] which properly dispatches [WindowInsets] to all children,
 * regardless of whether a child consumes the insets.
 *
 * Without this, views (say at index 0) consuming the insets, result in no other views receiving
 * the insets.
 */
class InsetDispatchingCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {
    override fun dispatchApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        val dispatched = super.dispatchApplyWindowInsets(insets)
        return dispatched
    }
}