package com.wallpaper.unsplash.photo.view.holder;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpaper.unsplash.common.ui.widget.CircleImageView;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.helper.ImageHelper;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.photo.view.activity.PhotoActivity;
import com.wallpaper.unsplash.user.view.activity.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** <br> Story holder. */

public class StoryHolder extends PhotoInfoAdapter.ViewHolder {

    @BindView(R.id.item_photo_story_title)
    TextView title;

    @BindView(R.id.item_photo_story_subtitle)
    TextView subtitle;

    @BindView(R.id.item_photo_story_content)
    TextView content;

    @BindView(R.id.item_photo_story_avatar)
    CircleImageView avatar;

    private Photo photo;

    public static final int TYPE_STORY = 4;

    public StoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        DisplayUtils.setTypeface(UnsplashApplication.getInstance().getTopActivity(), subtitle);
        DisplayUtils.setTypeface(UnsplashApplication.getInstance().getTopActivity(), content);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindView(PhotoActivity activity, Photo photo) {
        if (TextUtils.isEmpty(photo.story.description)) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            content.setText(photo.story.description);
        }

        if (TextUtils.isEmpty(photo.story.title)) {
            title.setText("A Story");
        } else {
            title.setText(photo.story.title);
        }

        subtitle.setText(activity.getString(R.string.by) + " " + photo.user.name);

        ImageHelper.loadAvatar(activity, avatar, photo.user);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            avatar.setTransitionName(photo.user.username + "-3");
        }

        this.photo = photo;
    }

    @Override
    protected void onRecycled() {
        ImageHelper.releaseImageView(avatar);
    }

    @OnClick(R.id.item_photo_story_avatar) void checkAuthor() {
        IntentHelper.startUserActivity(
                UnsplashApplication.getInstance().getTopActivity(),
                avatar,
                photo.user,
                UserActivity.PAGE_PHOTO);
    }
}
