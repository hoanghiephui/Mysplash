package com.wallpapers.unsplash.ui.adapters.exprore

import android.app.Activity
import com.wallpapers.unsplash.Constants
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo
import com.wallpapers.unsplash.common.utils.DisplayUtils
import com.wallpapers.unsplash.kotlin.extensions.autoNotify
import com.wallpapers.unsplash.ui.adapters.AdsDelegate
import com.wallpapers.unsplash.ui.adapters.FooterDelegate
import com.wallpapers.unsplash.ui.adapters.base.AdapterDelegatesManager
import com.wallpapers.unsplash.ui.adapters.base.DelegationAdapter
import kotlin.properties.Delegates

/**
 * Created by hoanghiep on 1/17/18.
 */
class ExproreDelegateAdapter(activity: Activity, private val callback: ExproreDelegate.CallBack)
    : DelegationAdapter<Any>(AdapterDelegatesManager(), emptyList()), ExproreDelegate.CallBack {

    override fun onClick(position: Int) {
        callback.onClick(position)
    }

    private val exproreDelegate = ExproreDelegate<Any>(activity, this)
    private val footer = FooterDelegate<Any>()
    private val adsDelegate = AdsDelegate<Any>(activity, idAdsFb = activity.getString(R.string.native_fb), idAdsGg = Constants.ADMOB_BANNER_EXPLORE)

    init {
        // Add all necessary AdapterDelegate objects here
        delegatesManager.apply {
            addAdapterDelegate(exproreDelegate)
            addAdapterDelegate(exproreDelegate)
            addAdapterDelegate(adsDelegate)
            addAdapterDelegate(exproreDelegate)
            addAdapterDelegate(exproreDelegate)
            addAdapterDelegate(exproreDelegate)
            addAdapterDelegate(adsDelegate)
            addAdapterDelegate(exproreDelegate)
            addAdapterDelegate(exproreDelegate)
            addAdapterDelegate(exproreDelegate)
            if (!DisplayUtils.isLandscape(activity) && DisplayUtils.getNavigationBarHeight(activity.getResources()) != 0) {
                addAdapterDelegate(footer)
            }
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
    override var items: List<Any> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o == n }
    }

    fun updatePhoto(photo: List<Photo>?) {
        exproreDelegate.updateBusinessPhoto(photo)
        notifyItemChanged(0)
    }

    fun updatePhotoGirl(photo: List<Photo>?) {
        exproreDelegate.updateGirl(photo)
        notifyItemChanged(1)
    }

    fun updatePhotoNature(list: List<Photo>?) {
        exproreDelegate.updatePhotoNature(list)
        notifyItemChanged(3)
    }

    fun updatePhotoTechnology(list: List<Photo>?) {
        exproreDelegate.updatePhotoTechnology(list)
        notifyItemChanged(4)
    }

    fun updatePhotoFood(list: List<Photo>?) {
        exproreDelegate.updatePhotoFood(list)
        notifyItemChanged(5)
    }

    fun updatePhotoTravel(list: List<Photo>?) {
        exproreDelegate.updatePhotoTravel(list)
        notifyItemChanged(7)
    }

    fun updatePhotoHappy(list: List<Photo>?) {
        exproreDelegate.updatePhotoHappy(list)
        notifyItemChanged(8)
    }

    fun updatePhotoCool(list: List<Photo>?) {
        exproreDelegate.updatePhotoCool(list)
        notifyItemChanged(9)
    }

    fun onComplete() {
        exproreDelegate.onComplete()
        adsDelegate.showAds()
    }

    fun onError() {
        exproreDelegate.onError()
    }

}