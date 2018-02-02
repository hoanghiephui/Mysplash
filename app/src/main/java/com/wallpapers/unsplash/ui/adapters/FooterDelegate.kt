package com.wallpapers.unsplash.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.common.basic.FooterAdapter
import com.wallpapers.unsplash.exprore.ExprorePresenter
import com.wallpapers.unsplash.kotlin.extensions.inflate
import com.wallpapers.unsplash.ui.adapters.base.AdapterDelegate

/**
 * Created by hoanghiep on 1/17/18.
 */
class FooterDelegate<in T> : AdapterDelegate<T> {
    override fun isForViewType(items: List<T>, position: Int): Boolean = items[position] is ExprorePresenter.Footer

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = FooterAdapter.FooterHolder(parent.inflate(R.layout.item_footer))

    override fun onBindViewHolder(items: List<T>, position: Int, holder: RecyclerView.ViewHolder, payloads: List<*>) {
        if ((holder is FooterAdapter.FooterHolder)) {
            Log.d("FooterDelegate", ":cccc ");
        }
    }

    override fun onViewDetachedFromWindow() {

    }
}