package com.wallpaper.unsplash

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet

/**
 * Created by hoanghiep on 12/8/17.
 */
class CustomToolbar : Toolbar {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)

    fun bindToActivity(activity: AppCompatActivity, showBackArrow: Boolean = true) {
        activity.setSupportActionBar(this)
        activity.supportActionBar?.let {
            with(it) {
                setHomeButtonEnabled(showBackArrow)
                setDisplayHomeAsUpEnabled(showBackArrow)
                setDisplayShowHomeEnabled(showBackArrow)
            }
        }
        if (showBackArrow) navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_toolbar_back_dark)
    }

    fun enableScroll(enable: Boolean, behavior: AppBarLayout.Behavior = AppBarLayout.Behavior()) {
        if (parent is AppBarLayout) {
            val parentView = parent as AppBarLayout
            val params = layoutParams as AppBarLayout.LayoutParams
            val appBarLayoutParams = parentView.layoutParams as CoordinatorLayout.LayoutParams
            if (enable) {
                params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                appBarLayoutParams.behavior = behavior as CoordinatorLayout.Behavior<*>?
                parentView.layoutParams = appBarLayoutParams
            } else {
                params.scrollFlags = 0
                appBarLayoutParams.behavior = null
                parentView.layoutParams = appBarLayoutParams
            }
        }
    }
}