package com.wallpapers.unsplash.common.interfaces.view;

import android.support.annotation.Nullable;
import android.view.View;

import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.interfaces.model.LoadModel;

/**
 * Load view.
 *
 * A view which can control multiple display states.
 *
 * */

public interface LoadView {

    void animShow(View v);
    void animHide(View v);

    void setLoadingState(@Nullable MysplashActivity activity, @LoadModel.StateRule int old);
    void setFailedState(@Nullable MysplashActivity activity, @LoadModel.StateRule int old);
    void setNormalState(@Nullable MysplashActivity activity, @LoadModel.StateRule int old);
}
