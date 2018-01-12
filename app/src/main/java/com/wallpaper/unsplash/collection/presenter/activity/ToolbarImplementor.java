package com.wallpaper.unsplash.collection.presenter.activity;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpaper.unsplash.common.data.entity.unsplash.Collection;
import com.wallpaper.unsplash.common.interfaces.presenter.ToolbarPresenter;
import com.wallpaper.unsplash.common.utils.ShareUtils;
import com.wallpaper.unsplash.collection.view.activity.CollectionActivity;

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
            case R.id.action_share: {
                Collection c = ((CollectionActivity) a).getCollection();
                ShareUtils.shareCollection(c);
                break;
            }
            case R.id.action_menu: {
                ((CollectionActivity) a).showPopup();
                break;
            }
        }
        return true;
    }
}
