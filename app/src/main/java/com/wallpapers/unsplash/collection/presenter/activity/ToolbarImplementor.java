package com.wallpapers.unsplash.collection.presenter.activity;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpapers.unsplash.common.data.entity.unsplash.Collection;
import com.wallpapers.unsplash.common.interfaces.presenter.ToolbarPresenter;
import com.wallpapers.unsplash.common.utils.ShareUtils;
import com.wallpapers.unsplash.collection.view.activity.CollectionActivity;

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
