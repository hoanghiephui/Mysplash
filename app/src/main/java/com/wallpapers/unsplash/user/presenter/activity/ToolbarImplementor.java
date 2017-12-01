package com.wallpapers.unsplash.user.presenter.activity;

import android.text.TextUtils;
import android.widget.Toast;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.interfaces.presenter.ToolbarPresenter;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpapers.unsplash.common.utils.ShareUtils;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.user.view.activity.UserActivity;

/**
 * Toolbar implementor.
 * */

public class ToolbarImplementor
        implements ToolbarPresenter {

    @Override
    public void touchNavigatorIcon(MysplashActivity a) {
        a.finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
    }

    @Override
    public void touchToolbar(MysplashActivity a) {
        // do nothing.
    }

    @Override
    public boolean touchMenuItem(MysplashActivity a, int itemId) {
        UserActivity activity = (UserActivity) a;

        switch (itemId) {
            case R.id.action_open_portfolio:
                String url = activity.getUserPortfolio();
                if (!TextUtils.isEmpty(url)) {
                    IntentHelper.startWebActivity(a, url);
                } else {
                    Toast.makeText(
                            activity,
                            a.getString(R.string.feedback_portfolio_is_null),
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.action_share:
                ShareUtils.shareUser(activity.getUser());
                break;

            case R.id.action_filter:
                activity.showPopup();
                break;
        }
        return true;
    }
}