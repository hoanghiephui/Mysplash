package com.wallpapers.unsplash.common._basic.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;

/**
 * Unsplash dialog fragment.
 *
 * Basic DialogFragment class for Unsplash.
 *
 * */

public abstract class MysplashDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MysplashActivity activity = Unsplash.getInstance().getTopActivity();
        if (activity != null) {
            activity.getDialogList().add(this);
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MysplashActivity activity = Unsplash.getInstance().getTopActivity();
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
