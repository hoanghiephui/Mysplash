package com.wallpapers.unsplash.ui.base

/**
 * Created by hoanghiep on 1/17/18.
 */
interface Presenter<VIEW : View> {

    val view: VIEW?

    fun onViewDestroyed()

    fun onViewPaused()

    fun initView(view: VIEW)

}