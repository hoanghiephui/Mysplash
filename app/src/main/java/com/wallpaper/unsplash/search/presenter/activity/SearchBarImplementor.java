package com.wallpaper.unsplash.search.presenter.activity;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.interfaces.presenter.SearchBarPresenter;
import com.wallpaper.unsplash.common.interfaces.view.SearchBarView;
import com.wallpaper.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Search bar implementor.
 *
 * */

public class SearchBarImplementor
        implements SearchBarPresenter {

    private SearchBarView view;

    public SearchBarImplementor(SearchBarView view) {
        this.view = view;
    }

    @Override
    public void touchNavigatorIcon(BaseActivity a) {
        a.finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
    }

    @Override
    public boolean touchMenuItem(BaseActivity a, int itemId) {
        switch (itemId) {
            case R.id.action_clear_text:
                view.clearSearchBarText();
                break;
        }
        return true;
    }

    @Override
    public void showKeyboard() {
        view.showKeyboard();
    }

    @Override
    public void hideKeyboard() {
        view.hideKeyboard();
    }

    @Override
    public void submitSearchInfo(String text) {
        view.submitSearchInfo(text);
    }
}
