package com.wallpapers.unsplash.ui.adapters.exprore

import android.app.Activity
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo
import com.wallpapers.unsplash.common.ui.widget.freedomSizeView.FreedomImageView
import com.wallpapers.unsplash.common.utils.helper.ImageHelper
import com.wallpapers.unsplash.kotlin.extensions.gone
import com.wallpapers.unsplash.kotlin.extensions.inflate
import com.wallpapers.unsplash.ui.adapters.base.AdapterDelegate
import kotlinx.android.synthetic.main.item_wallpaper_source.view.*

/**
 * Created by hoanghiep on 1/17/18.
 */
class ExproreItemDelegate<T>(activity: Activity) : AdapterDelegate<T>, ImageHelper.OnLoadImageListener<Photo> {
    override fun isForViewType(items: List<T>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ExproreItemViewHolder(parent.inflate(R.layout.item_wallpaper_source))

    override fun onBindViewHolder(items: List<T>, position: Int, holder: RecyclerView.ViewHolder, payloads: List<*>) {
        if (holder is ExproreItemViewHolder) {
            val photo = items[position] as Photo
            holder.image.setSize(photo.width, photo.height)
            ImageHelper.loadRegularPhoto(holder.itemView.context, holder.image, photo, position, this)
        }
    }

    class ExproreItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var image: FreedomImageView = itemView.item_wallpaper_source_cover
        internal var cardView: CardView = itemView.item_wallpaper_source
        internal var btnClose: AppCompatImageButton = itemView.item_wallpaper_source_deleteBtn

        init {
            btnClose.gone()
            image.setShowShadow(true)
            cardView.setRadius(3f)
            val params = cardView.getLayoutParams() as ViewGroup.MarginLayoutParams
            val margin = itemView.context.getResources().getDimensionPixelSize(R.dimen.little_margin)
            params.setMargins(margin, 0, 0, 0)
            cardView.layoutParams = params

        }
    }

    override fun onViewDetachedFromWindow() {

    }

    fun updatePhoto(list: List<Photo>?) {

    }

    override fun onLoadImageSucceed(newT: Photo?, index: Int) {
    }

    override fun onLoadImageFailed(originalT: Photo?, index: Int) {
    }
}