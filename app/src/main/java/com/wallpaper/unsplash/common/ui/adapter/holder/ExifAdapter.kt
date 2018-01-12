package com.wallpaper.unsplash.common.ui.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wallpaper.unsplash.R
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo
import com.wallpaper.unsplash.photo.view.holder.ExifHolder

/**
 * Created by hoanghiep on 12/2/17.
 */
class ExifAdapter(private val photo: Photo) : RecyclerView.Adapter<ExifHolder>() {
    override fun getItemCount(): Int {
        return 4
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ExifHolder {
        return ExifHolder(LayoutInflater.from(parent?.getContext()).inflate(R.layout.item_photo_exif, parent, false))    }

    override fun onBindViewHolder(holder: ExifHolder?, position: Int) {
        holder?.drawExif(holder.itemView.context, position, photo)
    }
}