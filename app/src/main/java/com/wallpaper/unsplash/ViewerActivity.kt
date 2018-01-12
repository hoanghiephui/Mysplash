package com.wallpaper.unsplash

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wallpaper.unsplash.common.basic.Previewable
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo
import com.wallpaper.unsplash.common.ui.activity.PreviewActivity.KEY_PREVIEW_ACTIVITY_PREVIEW
import kotlinx.android.synthetic.main.activity_view.*

/**
 * Created by hoanghiep on 12/8/17.
 */
class ViewerActivity : AppCompatActivity() {

    private var previewable: Previewable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        this.previewable = intent.getParcelableExtra<Parcelable>(KEY_PREVIEW_ACTIVITY_PREVIEW) as Previewable
        //this.showIcon = intent.getBooleanExtra(KEY_PREVIEW_ACTIVITY_SHOW_ICON, false)

        if (previewable is Photo) {
            Glide.with(this)
                    .load((previewable as Photo).getWallpaperSizeUrl(this))
                    .into(wallpapers)


        } else {
        }
    }
}