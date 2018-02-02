package com.wallpapers.unsplash.ui.adapters.exprore

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo
import com.wallpapers.unsplash.exprore.ExprorePresenter
import com.wallpapers.unsplash.kotlin.extensions.gone
import com.wallpapers.unsplash.kotlin.extensions.inflate
import com.wallpapers.unsplash.kotlin.unsafeLazy
import com.wallpapers.unsplash.ui.adapters.base.AdapterDelegate
import kotlinx.android.synthetic.main.item_root_exprore.view.*

/**
 * Created by hoanghiep on 1/17/18.
 */
class ExproreDelegate<in T>(private val activity: Activity, private val callback: CallBack) : AdapterDelegate<T> {
    private val exproreBusinessAdapter by unsafeLazy { activity.let { ExproreItemDelegateAdapter(it) } }
    private val exproreGrilAdapter by unsafeLazy { activity.let { ExproreItemDelegateAdapter(it) } }
    private val exproreNatureAdapter by unsafeLazy { activity.let { ExproreItemDelegateAdapter(it) } }
    private val exproreTechnologyAdapter by unsafeLazy { activity.let { ExproreItemDelegateAdapter(it) } }
    private val exproreFoodAdapter by unsafeLazy { activity.let { ExproreItemDelegateAdapter(it) } }
    private val exproreTravelAdapter by unsafeLazy { activity.let { ExproreItemDelegateAdapter(it) } }
    private val exproreHappyAdapter by unsafeLazy { activity.let { ExproreItemDelegateAdapter(it) } }
    private val exproreCoolAdapter by unsafeLazy { activity.let { ExproreItemDelegateAdapter(it) } }
    var holder: ExproreViewHolder? = null

    override fun isForViewType(items: List<T>, position: Int): Boolean {
        return items[position] is ExprorePresenter.ExproreDisplayable
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ExproreViewHolder(parent.inflate(R.layout.item_root_exprore))

    override fun onBindViewHolder(items: List<T>, position: Int, holder: RecyclerView.ViewHolder, payloads: List<*>) {
        if (holder is ExproreViewHolder) {
            this.holder = holder
            Log.d("ExproreDelegate", ": " + position)
            when (position) {
                0 -> {
                    holder.title.text = "Business images"
                    holder.content.text = "Download free business photos of real people getting ready for work in real life. No cheesy or stocky business pictures here."
                    holder.recyler.adapter = exproreBusinessAdapter
                }

                1 -> {
                    holder.title.text = "Girl images"
                    holder.content.text = "Download diverse photos of girls with various emotions from laughter, discovery, and wonder"
                    holder.recyler.adapter = exproreGrilAdapter
                }

                3 -> {
                    holder.title.text = "Nature images"
                    holder.content.text = "Travel to the scenic views with our most popular nature photos from rocky mountains to redwood forests. Discover and download these nature pictures for your blog or post to social media"
                    holder.recyler.adapter = exproreNatureAdapter
                }

                4 -> {
                    holder.title.text = "Technology images"
                    holder.content.text = "Browse these technology images featuring workspaces fill with gadgets, MacBooks, iPhones, and cameras."
                    holder.recyler.adapter = exproreTechnologyAdapter
                }

                5 -> {
                    holder.title.text = "Food images"
                    holder.content.text = "Get hungry with these beautiful pictures of food. Download our most popular food images featuring coffee and pastries, fast food, vegan food, Thai food, and Mexican food."
                    holder.recyler.adapter = exproreFoodAdapter
                }

                7 -> {
                    holder.title.text = "Travel images"
                    holder.content.text = "Get lost in our most popular travel photos featuring overseas adventures and backpacking journeys. Download these travel pictures to inspire your wanderlust."
                    holder.recyler.adapter = exproreTravelAdapter
                }

                8 -> {
                    holder.title.text = "Happy images"
                    holder.content.text = "Celebrate happy days full of smiling and joy. Download these popular happy images perfect for birthdays, mother's day, valentines day, and anniversary."
                    holder.recyler.adapter = exproreHappyAdapter
                }
                9 -> {
                    holder.title.text = "Cool images"
                    holder.content.text = "Download these cool animal pictures, cool nature pictures, and cool space pictures. Use these HD photos for your desktop wallpapers."
                    holder.recyler.adapter = exproreCoolAdapter
                }
            }
            holder.view.setOnClickListener({
                callback.onClick(position)
            })
        }
    }

    override fun onViewDetachedFromWindow() {

    }

    fun updateBusinessPhoto(list: List<Photo>?) {
        list?.let { exproreBusinessAdapter.items = it }
    }

    fun updateGirl(list: List<Photo>?) {
        list?.let { exproreGrilAdapter.items = it }
    }

    fun updatePhotoNature(list: List<Photo>?) {
        list?.let { exproreNatureAdapter.items = it }
    }

    fun updatePhotoTechnology(list: List<Photo>?) {
        list?.let { exproreTechnologyAdapter.items = it }
    }

    fun updatePhotoFood(list: List<Photo>?) {
        list?.let { exproreFoodAdapter.items = it }
    }

    fun updatePhotoTravel(list: List<Photo>?) {
        list?.let { exproreTravelAdapter.items = it }

    }
    fun updatePhotoHappy(list: List<Photo>?) {
        holder?.progress?.gone()
        list?.let { exproreHappyAdapter.items = it }
    }

    fun updatePhotoCool(list: List<Photo>?) {
        list?.let { exproreCoolAdapter.items = it }
    }

    fun onComplete() {
        holder?.progress?.gone()
    }

    fun onError() {
        holder?.progress?.gone()
    }

    class ExproreViewHolder internal constructor(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        internal var title: TextView = itemView.title
        internal var content: TextView = itemView.content
        internal var recyler: RecyclerView = itemView.recycler
        internal var progress: ProgressBar = itemView.progress
        internal var view: LinearLayout = itemView.view
        init {
            progress.gone()
            recyler.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }

    }

    interface CallBack {
        fun onClick(position: Int);
    }
}