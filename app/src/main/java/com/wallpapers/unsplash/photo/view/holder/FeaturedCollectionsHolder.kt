package com.wallpapers.unsplash.photo.view.holder

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.TextView
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.common.basic.activity.BaseActivity
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo
import com.wallpapers.unsplash.common.ui.adapter.CollectionAdapter
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter
import com.wallpapers.unsplash.common.ui.widget.SwipeSwitchLayout
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity

/**
 * Created by hoanghiep on 12/4/17.
 */
class FeaturedCollectionsHolder(itemView: View, activity: BaseActivity) : PhotoInfoAdapter.ViewHolder(itemView) {
    companion object {
        const val TYPE_COLLECTION = 11
    }

    var recyclerView: SwipeSwitchLayout.RecyclerView? = null
    var tile: TextView? = null

    init {
        recyclerView = itemView.findViewById(R.id.item_photo_tag) as SwipeSwitchLayout.RecyclerView
        tile = itemView.findViewById(R.id.title)
        recyclerView?.setLayoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
        recyclerView?.setHasFixedSize(true)
        val margin = activity.getResources().getDimensionPixelSize(R.dimen.little_margin)
        recyclerView?.setPadding(margin, margin, 0, 0)
    }


    override fun onBindView(activity: PhotoActivity?, photo: Photo?) {
        tile?.text = activity?.getString(R.string.related_collections, photo?.related_collections?.total)
        recyclerView?.adapter = CollectionAdapter(activity, photo?.related_collections?.results, false, 2)

    }

    override fun onRecycled() {
        ((recyclerView?.adapter) as CollectionAdapter).onDetachedFromRecyclerView(recyclerView)
    }

    fun scrollTo(x: Int, y: Int) {
        recyclerView?.scrollTo(x, y)
    }

    fun setScrollListener(listener: RecyclerView.OnScrollListener) {
        recyclerView?.clearOnScrollListeners()
        recyclerView?.addOnScrollListener(listener)
    }
}