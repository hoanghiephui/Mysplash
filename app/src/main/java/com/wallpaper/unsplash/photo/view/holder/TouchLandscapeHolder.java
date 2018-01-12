package com.wallpaper.unsplash.photo.view.holder;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpaper.unsplash.common.ui.adapter.holder.ExifAdapter;
import com.wallpaper.unsplash.common.ui.widget.CircleImageView;
import com.wallpaper.unsplash.common.ui.widget.freedomSizeView.FreedomTouchView;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.ShareUtils;
import com.wallpaper.unsplash.common.utils.helper.ImageHelper;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.photo.view.activity.PhotoActivity;
import com.wallpaper.unsplash.user.view.activity.UserActivity;

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
    @BindView(R.id.countDownload)
    TextView countDownload;
    @BindView(R.id.countLike)
    TextView countLike;
    @BindView(R.id.countView)
    TextView countView;

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
    protected void onBindView(PhotoActivity activity, Photo photo) {
        this.photo = photo;

        touchView.setSize(photo.width, photo.height);
        touchView.setShowShadow(true);

        ImageHelper.loadAvatar(activity, avatar, photo.user);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            avatar.setTransitionName(photo.user.username + "-2");
        }

        title.setText(activity.getString(R.string.by) + " " + photo.user.name);
        String locationText;
        if (photo.location == null
                || (photo.location.city == null && photo.location.country == null)) {
            locationText = "Unknown";
        } else {
            locationText = photo.location.city == null ? "" : photo.location.city + ", ";
            locationText = locationText + (photo.location.country == null ? "" : photo.location.country);
        }
        subtitle.setText(activity.getString(R.string.published_on) + " " +
                photo.created_at.split("T")[0] + " " + activity.getString(R.string.at) + " " + locationText);
        countDownload.setText(String.valueOf(photo.downloads));
        countLike.setText(String.valueOf(photo.likes));
        countView.setText(String.valueOf(photo.views));
    }

    @Override
    protected void onRecycled() {
        ImageHelper.releaseImageView(avatar);
    }

    @Override
    protected void updateData(Photo photo) {
        this.photo = photo;
    }

    @OnClick(R.id.item_photo_touch_landscape_touch)
    void clickTouchView() {
        IntentHelper.startPreviewActivity(
                UnsplashApplication.getInstance().getTopActivity(), activity.getPhoto(), true);
    }

    @OnClick(R.id.item_photo_touch_landscape_avatar) void clickAvatar() {
        IntentHelper.startUserActivity(
                UnsplashApplication.getInstance().getTopActivity(),
                avatar,
                photo.user,
                UserActivity.PAGE_PHOTO);
    }

    @OnClick(R.id.item_photo_touch_landscape_shareBtn) void share() {
        ShareUtils.sharePhoto(photo);
    }

    private RecyclerView recyclerView;
    @OnClick(R.id.item_photo_touch_landscape_menuBtn) void checkMenu() {
        //activity.showPopup(activity, menuBtn, null, 0);
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);
        View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_recy, null, false);
        recyclerView = sheetView.findViewById(R.id.recy);

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new ExifAdapter(photo));
    }

    public void updataPhoto(Photo photo) {
        this.photo = photo;
        if (recyclerView != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
