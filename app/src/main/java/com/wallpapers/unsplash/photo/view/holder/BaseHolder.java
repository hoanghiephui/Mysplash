package com.wallpapers.unsplash.photo.view.holder;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpapers.unsplash.common.ui.widget.CircleImageView;
import com.wallpapers.unsplash.common.ui.widget.PhotoButtonBar;
import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpapers.unsplash.common.utils.AnimUtils;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.ShareUtils;
import com.wallpapers.unsplash.common.utils.helper.DatabaseHelper;
import com.wallpapers.unsplash.common.utils.helper.DownloadHelper;
import com.wallpapers.unsplash.common.utils.helper.ImageHelper;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;
import com.wallpapers.unsplash.common.utils.manager.ThemeManager;
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity;
import com.wallpapers.unsplash.user.view.activity.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Base holder.
 *
 * This view holder is used to show the basic part of the photo information.
 *
 * */

public class BaseHolder extends PhotoInfoAdapter.ViewHolder
        implements View.OnClickListener, Toolbar.OnMenuItemClickListener,
        PhotoButtonBar.OnClickButtonListener {

    private PhotoActivity activity;

    @BindView(R.id.item_photo_base_displayContainer)
    RelativeLayout displayContainer;

    @BindView(R.id.item_photo_base_toolbar)
    Toolbar toolbar;

    @BindView(R.id.item_photo_base_title)
    TextView title;

    @BindView(R.id.item_photo_base_subtitle)
    TextView subtitle;

    @BindView(R.id.item_photo_base_avatar)
    CircleImageView avatar;

    @BindView(R.id.item_photo_base_btnBar)
    PhotoButtonBar buttonBar;

    private Photo photo;

    public static final int TYPE_BASE = 2;

    public BaseHolder(PhotoActivity a, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.activity = a;

        toolbar.setTitle("");
        if (Unsplash.getInstance().getActivityCount() == 1) {
            ThemeManager.setNavigationIcon(
                    toolbar, R.drawable.ic_toolbar_home_light, R.drawable.ic_toolbar_home_dark);
        } else {
            ThemeManager.setNavigationIcon(
                    toolbar, R.drawable.ic_toolbar_back_light, R.drawable.ic_toolbar_back_dark);
        }
        ThemeManager.inflateMenu(
                toolbar, R.menu.activity_photo_toolbar_light, R.menu.activity_photo_toolbar_dark);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);

        DisplayUtils.setTypeface(activity, subtitle);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindView(PhotoActivity a, Photo photo) {
        title.setText(a.getString(R.string.by) + " " + photo.user.name);
        subtitle.setText(a.getString(R.string.on) + " " + photo.created_at.split("T")[0]);

        ImageHelper.loadAvatar(a, avatar, photo.user);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            avatar.setTransitionName(photo.user.username + "-1");
        }

        buttonBar.setState(photo);
        if (DatabaseHelper.getInstance(a).readDownloadingEntityCount(photo.id) > 0) {
            a.startCheckDownloadProgressThread();
        }
        buttonBar.setOnClickButtonListener(this);

        this.photo = photo;
    }

    @Override
    protected void onRecycled() {
        ImageHelper.releaseImageView(avatar);
    }

    public void showInitAnim() {
        displayContainer.setVisibility(View.GONE);
        buttonBar.setVisibility(View.GONE);
        AnimUtils.animInitShow(displayContainer, 200);
        AnimUtils.animInitShow(buttonBar, 350);
    }

    public PhotoButtonBar getButtonBar() {
        return buttonBar;
    }

    // interface.

    // on click listener.

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case -1:
                if (Unsplash.getInstance().getActivityCount() == 1) {
                    activity.visitParentActivity();
                }
                activity.finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
                break;
        }
    }

    @OnClick(R.id.item_photo_base_avatar) void clickAvatar() {
        IntentHelper.startUserActivity(
                Unsplash.getInstance().getTopActivity(),
                avatar,
                photo.user,
                UserActivity.PAGE_PHOTO);
    }

    // on menu item click listener.

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                ShareUtils.sharePhoto(photo);
                break;

            case R.id.action_menu:
                activity.showPopup(activity, toolbar, null, 0);
                break;
        }
        return true;
    }

    // on click button listener.

    @Override
    public void onLikeButtonClicked() {
        if (AuthManager.getInstance().isAuthorized()) {
            activity.likePhoto();
        } else {
            IntentHelper.startLoginActivity(activity);
        }
    }

    @Override
    public void onCollectButtonClicked() {
        if (AuthManager.getInstance().isAuthorized()) {
            activity.collectPhoto();
        } else {
            IntentHelper.startLoginActivity(activity);
        }
    }

    @Override
    public void onDownloadButtonClicked() {
        activity.readyToDownload(DownloadHelper.DOWNLOAD_TYPE, true);
    }

    @Override
    public void onDownloadButtonLongClicked() {
        activity.readyToDownload(DownloadHelper.DOWNLOAD_TYPE);
    }
}
