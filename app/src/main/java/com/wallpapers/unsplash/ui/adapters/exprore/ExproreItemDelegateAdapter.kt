package com.wallpapers.unsplash.ui.adapters.exprore

import android.app.Activity
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo
import com.wallpapers.unsplash.kotlin.extensions.autoNotify
import com.wallpapers.unsplash.ui.adapters.base.AdapterDelegatesManager
import com.wallpapers.unsplash.ui.adapters.base.DelegationAdapter
import kotlin.properties.Delegates

/**
 * Created by hoanghiep on 1/17/18.
 */
class ExproreItemDelegateAdapter(activity: Activity)
    : DelegationAdapter<Photo>(AdapterDelegatesManager(), emptyList()) {
    private val exproreItemDelegate = ExproreItemDelegate<Photo>(activity)

    init {
        delegatesManager.apply {
            addAdapterDelegate(exproreItemDelegate)
        }
    }

    /**
     * Required so that [setHasStableIds] = true doesn't break the RecyclerView and show duplicated
     * layouts.
     */
    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    /**
     * Observes the items list and automatically notifies the adapter of changes to the data based
     * on the comparison we make here, which is a simple equality check.
     */
    override var items: List<Photo> by Delegates.observable(emptyList()) {
        _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o == n }
    }

    /*fun updateBusinessPhoto(list: List<Photo>?) {
        exproreItemDelegate.updateBusinessPhoto(list)
        notifyDataSetChanged()
    }

    fun updateGirl(list: List<Photo>?) {
        exproreItemDelegate.updateBusinessPhoto(list)
        notifyDataSetChanged()
    }*/
}