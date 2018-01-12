package com.wallpaper.unsplash.common.interfaces.view;

import android.support.annotation.Nullable;
import android.view.View;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.interfaces.model.LoadModel;

/**
 * Load view.
 *
 * A view which can control multiple display states.
 *
 * */

public interface LoadView {

    void animShow(View v);
    void animHide(View v);

    void setLoadingState(@Nullable BaseActivity activity, @LoadModel.StateRule int old);
    void setFailedState(@Nullable BaseActivity activity, @LoadModel.StateRule int old);
    void setNormalState(@Nullable BaseActivity activity, @LoadModel.StateRule int old);
}
