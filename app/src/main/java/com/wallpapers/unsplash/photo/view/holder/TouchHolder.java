package com.wallpapers.unsplash.photo.view.holder;

import android.view.View;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpapers.unsplash.common.ui.widget.freedomSizeView.FreedomTouchView;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Touch holder.
 */

public class TouchHolder extends PhotoInfoAdapter.ViewHolder {

    @BindView(R.id.item_photo_touch)
    FreedomTouchView touchView;

    private PhotoActivity activity;

    public static final int TYPE_TOUCH = 1;

    public TouchHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void onBindView(PhotoActivity activity, Photo photo) {
        touchView.setSize(photo.width, photo.height);
        touchView.setShowShadow(false);
        this.activity = activity;
    }

    @Override
    protected void onRecycled() {
        // do nothing.
    }

    @OnClick(R.id.item_photo_touch)
    void clickTouchView() {
        IntentHelper.startPreviewActivity(
                UnsplashApplication.getInstance().getTopActivity(), activity.getPhoto(), true);
    }
}
