package com.wallpaper.unsplash.common.interfaces.presenter;

import android.support.annotation.NonNull;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;

/**
 * Load presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.LoadView}.
 *
 * */

public interface LoadPresenter {

    void bindActivity(@NonNull BaseActivity activity);

    int getLoadState();

    void setLoadingState();
    void setFailedState();
    void setNormalState();
}
