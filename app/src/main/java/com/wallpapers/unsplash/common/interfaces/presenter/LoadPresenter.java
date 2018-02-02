package com.wallpapers.unsplash.common.interfaces.presenter;

import android.support.annotation.NonNull;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;

/**
 * Load presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.LoadView}.
 */

public interface LoadPresenter {

    void bindActivity(@NonNull BaseActivity activity);

    int getLoadState();

    void setLoadingState();

    void setFailedState();

    void setNormalState();
}
