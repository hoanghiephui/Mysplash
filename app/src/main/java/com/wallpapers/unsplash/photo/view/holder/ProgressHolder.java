package com.wallpapers.unsplash.photo.view.holder;

import android.view.View;
import android.widget.Button;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpapers.unsplash.common.utils.AnimUtils;
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Progress holder.
 * <p>
 * This view holder is used to show the progress.
 */

public class ProgressHolder extends PhotoInfoAdapter.ViewHolder {

    @BindView(R.id.item_photo_progress_progressView)
    CircularProgressView progress;

    @BindView(R.id.item_photo_progress_button)
    Button button;

    private boolean failed;
    public static final int TYPE_PROGRESS = 3;

    public ProgressHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void onBindView(PhotoActivity activity, Photo photo) {
        failed = activity.isLoadFailed();
        if (failed) {
            progress.setAlpha(0f);
            button.setAlpha(1f);
            button.setVisibility(View.VISIBLE);
        } else {
            progress.setAlpha(1f);
            button.setAlpha(0f);
            button.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRecycled() {
        // do nothing.
    }

    public void setFailedState() {
        if (!failed) {
            failed = true;
            AnimUtils.animShow(button, 150, button.getAlpha(), 1f);
            AnimUtils.animHide(progress, 150, progress.getAlpha(), 0f, false);
        }
    }

    private void setProgressState() {
        if (failed) {
            failed = false;
            AnimUtils.animShow(progress, 150, progress.getAlpha(), 1f);
            AnimUtils.animHide(button, 150, button.getAlpha(), 0f, true);
        }
    }

    @OnClick(R.id.item_photo_progress_button)
    void retryRefresh() {
        BaseActivity activity = UnsplashApplication.getInstance().getTopActivity();
        if (activity != null && activity instanceof PhotoActivity) {
            setProgressState();
            ((PhotoActivity) activity).initRefresh();
        }
    }
}
