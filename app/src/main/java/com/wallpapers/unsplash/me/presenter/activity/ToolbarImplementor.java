package com.wallpapers.unsplash.me.presenter.activity;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;
import com.wallpapers.unsplash.common.interfaces.presenter.ToolbarPresenter;
import com.wallpapers.unsplash.me.view.activity.MeActivity;

/**
 * Toolbar implementor.
 * */

public class ToolbarImplementor
        implements ToolbarPresenter {

    @Override
    public void touchNavigatorIcon(BaseActivity a) {
        a.finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
    }

    @Override
    public void touchToolbar(BaseActivity a) {
        // do nothing.
    }

    @Override
    public boolean touchMenuItem(BaseActivity a, int itemId) {
        switch (itemId) {
            case R.id.action_edit:
                if (AuthManager.getInstance().isAuthorized()
                        && AuthManager.getInstance().getMe() != null) {
                    IntentHelper.startUpdateMeActivity(a);
                }
                break;

            case R.id.action_filter:
                if (AuthManager.getInstance().isAuthorized()
                        && AuthManager.getInstance().getMe() != null) {
                    ((MeActivity) a).showPopup(true);
                }
                break;

            case R.id.action_menu:
                if (AuthManager.getInstance().isAuthorized()
                        && AuthManager.getInstance().getMe() != null) {
                    ((MeActivity) a).showPopup(false);
                }
                break;
        }
        return true;
    }
}
