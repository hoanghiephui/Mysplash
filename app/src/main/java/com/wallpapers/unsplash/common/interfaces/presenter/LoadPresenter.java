package com.wallpapers.unsplash.common.interfaces.presenter;

import android.support.annotation.NonNull;

import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;

/**
 * Load presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.LoadView}.
 *
 * */

public interface LoadPresenter {

    void bindActivity(@NonNull MysplashActivity activity);

    int getLoadState();

    void setLoadingState();
    void setFailedState();
    void setNormalState();
}
