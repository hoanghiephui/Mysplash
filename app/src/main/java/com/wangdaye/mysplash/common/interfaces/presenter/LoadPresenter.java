package com.wangdaye.mysplash.common.interfaces.presenter;

import android.support.annotation.NonNull;

import com.wangdaye.mysplash.common._basic.activity.MysplashActivity;

/**
 * Load presenter.
 *
 * Presenter for {@link com.wangdaye.mysplash.common.interfaces.view.LoadView}.
 *
 * */

public interface LoadPresenter {

    void bindActivity(@NonNull MysplashActivity activity);

    int getLoadState();

    void setLoadingState();
    void setFailedState();
    void setNormalState();
}
