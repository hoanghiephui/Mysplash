package com.wallpapers.unsplash.common.interfaces.model;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;

/**
 * Load model.
 * <p>
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.LoadView}.
 */

public interface LoadModel {

    int LOADING_STATE = 0;
    int FAILED_STATE = -1;
    int NORMAL_STATE = 1;

    @IntDef({LOADING_STATE, FAILED_STATE, NORMAL_STATE})
    @interface StateRule {
    }

    @Nullable
    BaseActivity getActivity();

    void setActivity(@NonNull BaseActivity activity);

    @StateRule
    int getState();

    void setState(@StateRule int state);
}
