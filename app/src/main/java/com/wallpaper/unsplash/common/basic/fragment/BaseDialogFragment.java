package com.wallpaper.unsplash.common.basic.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;

/**
 * UnsplashApplication dialog fragment.
 *
 * Basic DialogFragment class for UnsplashApplication.
 *
 * */

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BaseActivity activity = UnsplashApplication.getInstance().getTopActivity();
        if (activity != null) {
            activity.getDialogList().add(this);
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseActivity activity = UnsplashApplication.getInstance().getTopActivity();
        if (activity != null) {
            activity.getDialogList().remove(this);
        }
    }

    /**
     * Get the container CoordinatorLayout of snack bar.
     *
     * @return The container layout of snack bar.
     * */
    public abstract CoordinatorLayout getSnackbarContainer();
}
