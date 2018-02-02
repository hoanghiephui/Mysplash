package com.wallpapers.unsplash.common.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.UnsplashApplication
import com.wallpapers.unsplash.common.data.entity.unsplash.Tag
import com.wallpapers.unsplash.common.utils.helper.IntentHelper

/**
 * Created by hoanghiep on 1/15/18.
 */
class MiniTagAdapter(private val itemList: List<Tag>) : RecyclerView.Adapter<MiniTagAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var text: TextView? = null

        init {
            text = itemView.findViewById(R.id.item_tag_mini_text)
            itemView.setOnClickListener {
                clickItem()
            }
        }

        fun onBindView(position: Int) {
            text?.setText(itemList[position].getTitle())
        }

        // interface.

        fun clickItem() {
            IntentHelper.startSearchActivity(
                    UnsplashApplication.getInstance().getTopActivity(),
                    itemList[adapterPosition].getTitle())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_mini, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}