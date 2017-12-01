package com.wallpapers.unsplash.common._basic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;

/**
 * Unsplash popup window.
 *
 * Basic PopupWindow class for Unsplash.
 *
 * */

public class MysplashPopupWindow extends PopupWindow {

    public MysplashPopupWindow(Context context) {
        super(context);
        final MysplashActivity activity = Unsplash.getInstance().getTopActivity();
        if (activity != null) {
            activity.getPopupList().add(this);
            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    activity.getPopupList().remove(MysplashPopupWindow.this);
                }
            });
        }
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(context.getResources().getDimensionPixelSize(R.dimen.low_elevation));
        }
    }

    public void setContentView(View contentView) {
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        super.setContentView(contentView);
    }

    @SuppressLint("RtlHardcoded")
    protected void show(View anchor, int offsetX, int offsetY) {
        int[] locations = new int[2];
        anchor.getLocationOnScreen(locations);
        locations[0] += offsetX + getContentView().getMeasuredWidth();
        locations[1] += offsetY + getContentView().getMeasuredHeight();

        int[] screenSizes = new int[] {
                anchor.getContext().getResources().getDisplayMetrics().widthPixels,
                anchor.getContext().getResources().getDisplayMetrics().heightPixels};
        int[] triggers = new int[] {
                screenSizes[1]
                        - 6 * anchor.getResources().getDimensionPixelSize(R.dimen.normal_margin),
                screenSizes[1]
                        - 6 * anchor.getResources().getDimensionPixelSize(R.dimen.normal_margin)};

        if (locations[0] <= triggers[0]) {
            if (locations[1] <= triggers[1]) {
                setAnimationStyle(R.style.MysplashPopupWindowAnimation_Top_Left);
                showAsDropDown(anchor, offsetX, offsetY, Gravity.LEFT);
            } else {
                setAnimationStyle(R.style.MysplashPopupWindowAnimation_Bottom_Left);
                showAsDropDown(
                        anchor,
                        offsetX,
                        offsetY - anchor.getMeasuredHeight() - getContentView().getMeasuredHeight(),
                        Gravity.LEFT);
            }
        } else {
            if (locations[1] <= triggers[1]) {
                setAnimationStyle(R.style.MysplashPopupWindowAnimation_Top_Right);
                showAsDropDown(anchor, offsetX, offsetY, Gravity.RIGHT);
            } else {
                setAnimationStyle(R.style.MysplashPopupWindowAnimation_Bottom_Right);
                showAsDropDown(
                        anchor,
                        offsetX,
                        offsetY - anchor.getMeasuredHeight() - getContentView().getMeasuredHeight(),
                        Gravity.RIGHT);
            }
        }
    }
}