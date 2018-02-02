package com.wallpapers.unsplash.exprore

import com.wallpapers.unsplash.ui.base.View

/**
 * Created by hoanghiep on 1/17/18.
 */
interface ExproreView : View {
    fun updatePhoto(photos: ExprorePresenter.ExproreData)

    fun onComplete()

    fun onError(message: String?)

    fun notifyItemAdded(displayItems: MutableList<Any>, position: Int)

    fun notifyItemRemoved(displayItems: MutableList<Any>, position: Int)
}