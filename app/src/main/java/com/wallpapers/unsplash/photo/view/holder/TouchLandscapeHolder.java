package com.wallpapers.unsplash.photo.view.holder;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpapers.unsplash.common.ui.widget.CircleImageView;
import com.wallpapers.unsplash.common.ui.widget.freedomSizeView.FreedomTouchView;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.ShareUtils;
import com.wallpapers.unsplash.common.utils.helper.ImageHelper;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity;
import com.wallpapers.unsplash.user.view.activity.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Touch holder.
 * */

public class TouchLandscapeHolder extends PhotoInfoAdapter.ViewHolder {

    private PhotoActivity activity;

    @BindView(R.id.item_photo_touch_landscape_touch)
    FreedomTouchView touchView;

    @BindView(R.id.item_photo_touch_landscape_avatar)
    CircleImageView avatar;

    @BindView(R.id.item_photo_touch_landscape_title)
    TextView title;

    @BindView(R.id.item_photo_touch_landscape_subtitle)
    TextView subtitle;

    @BindView(R.id.item_photo_touch_landscape_menuBtn)
    ImageButton menuBtn;

    private Photo photo;

    public static final int TYPE_TOUCH_LANDSCAPE = 8;

    public TouchLandscapeHolder(PhotoActivity a, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.activity = a;
        DisplayUtils.setTypeface(activity, subtitle);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindView(PhotoActivity a, Photo photo) {
        this.photo = photo;

        touchView.setSize(photo.width, photo.height);
        touchView.setShowShadow(true);

        ImageHelper.loadAvatar(a, avatar, photo.user);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            avatar.setTransitionName(photo.user.username + "-2");
        }

        title.setText(a.getString(R.string.by) + " " + photo.user.name);
        subtitle.setText(a.getString(R.string.on) + " " + photo.created_at.split("T")[0]);
    }

    @Override
    protected void onRecycled() {
        ImageHelper.releaseImageView(avatar);
    }

    @OnClick(R.id.item_photo_touch_landscape_touch)
    void clickTouchView() {
        IntentHelper.startPreviewActivity(
                Unsplash.getInstance().getTopActivity(), activity.getPhoto(), true);
    }

    @OnClick(R.id.item_photo_touch_landscape_avatar) void clickAvatar() {
        IntentHelper.startUserActivity(
                Unsplash.getInstance().getTopActivity(),
                avatar,
                photo.user,
                UserActivity.PAGE_PHOTO);
    }

    @OnClick(R.id.item_photo_touch_landscape_shareBtn) void share() {
        ShareUtils.sharePhoto(photo);
    }

    @OnClick(R.id.item_photo_touch_landscape_menuBtn) void checkMenu() {
        activity.showPopup(activity, menuBtn, null, 0);
    }
}
