package com.wallpapers.unsplash.common.interfaces.view;

import android.support.annotation.Nullable;
import android.view.View;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.interfaces.model.LoadModel;

/**
 * Load view.
 * <p>
 * A view which can control multiple display states.
 */

public interface LoadView {

    void animShow(View v);

    void animHide(View v);

    void setLoadingState(@Nullable BaseActivity activity, @LoadModel.StateRule int old);

    void setFailedState(@Nullable BaseActivity activity, @LoadModel.StateRule int old);

    void setNormalState(@Nullable BaseActivity activity, @LoadModel.StateRule int old);
}
