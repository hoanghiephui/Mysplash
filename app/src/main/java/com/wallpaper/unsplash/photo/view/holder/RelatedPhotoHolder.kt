package com.wallpaper.unsplash.photo.view.holder

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.TextView
import com.wallpaper.unsplash.R
import com.wallpaper.unsplash.common.basic.activity.BaseActivity
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo
import com.wallpaper.unsplash.common.ui.adapter.PhotoAdapter
import com.wallpaper.unsplash.common.ui.adapter.PhotoInfoAdapter
import com.wallpaper.unsplash.common.ui.widget.SwipeSwitchLayout
import com.wallpaper.unsplash.photo.view.activity.PhotoActivity

/**
 * Created by hoanghiep on 12/4/17.
 */
class RelatedPhotoHolder(itemView: View, activity: BaseActivity) : PhotoInfoAdapter.ViewHolder(itemView) {
    companion object {
        const val TYPE_PHOTO = 12
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

    override fun onBindView(activity: PhotoActivity?, photo: MutableList<Photo>?) {
        tile?.text = activity?.getString(R.string.related_photo)
        recyclerView?.adapter = PhotoAdapter(activity, photo, activity, activity, false, false)
        ((recyclerView?.adapter) as PhotoAdapter).setRecyclerView(recyclerView)
    }

    override fun onRecycled() {
        ((recyclerView?.adapter) as PhotoAdapter).onDetachedFromRecyclerView(recyclerView)
    }

    fun scrollTo(x: Int, y: Int) {
        recyclerView?.scrollTo(x, y)
    }

    fun setScrollListener(listener: RecyclerView.OnScrollListener) {
        recyclerView?.clearOnScrollListeners()
        recyclerView?.addOnScrollListener(listener)
    }
}