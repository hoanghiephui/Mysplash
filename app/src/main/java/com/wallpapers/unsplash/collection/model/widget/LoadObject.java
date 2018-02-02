package com.wallpapers.unsplash.collection.model.widget;

import android.support.annotation.NonNull;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.interfaces.model.LoadModel;

/**
 * Load object.
 */

public class LoadObject
        implements LoadModel {

    private BaseActivity activity;

    @StateRule
    private int state;

    public LoadObject(@StateRule int state) {
        this.state = state;
    }

    @Override
    public BaseActivity getActivity() {
        return activity;
    }

    @Override
    public void setActivity(@NonNull BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    @StateRule
    public int getState() {
        return state;
    }

    @Override
    public void setState(@StateRule int state) {
        this.state = state;
    }
}
