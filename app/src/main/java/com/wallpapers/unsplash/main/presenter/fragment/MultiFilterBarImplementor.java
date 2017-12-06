package com.wallpapers.unsplash.main.presenter.fragment;

import com.wallpapers.unsplash.common.interfaces.model.MultiFilterBarModel;
import com.wallpapers.unsplash.common.interfaces.presenter.MultiFilterBarPresenter;
import com.wallpapers.unsplash.common.interfaces.view.MultiFilterBarView;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.main.view.activity.MainActivity;
import com.wallpapers.unsplash.main.view.fragment.MultiFilterFragment;

/**
 * Multi-filter bar implementor.
 *
 * */

public class MultiFilterBarImplementor
        implements MultiFilterBarPresenter {

    private MultiFilterBarModel model;
    private MultiFilterBarView view;

    public MultiFilterBarImplementor(MultiFilterBarModel model, MultiFilterBarView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void touchNavigatorIcon() {
        view.touchNavigationIcon();
    }

    @Override
    public void touchToolbar(BaseActivity a) {
        MainActivity activity = (MainActivity) a;
        MultiFilterFragment f = (MultiFilterFragment) activity.getTopFragment();
        if (f != null) {
            f.backToTop();
        }
    }

    @Override
    public void touchSearchButton() {
        view.touchSearchButton();
    }

    @Override
    public void touchMenuContainer(int position) {
        view.touchMenuContainer(position);
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
    public void submitSearchInfo() {
        view.submitSearchInfo();
    }

    @Override
    public void setQuery(String query) {
        model.setQuery(query);
    }

    @Override
    public String getQuery() {
        return model.getQuery();
    }

    @Override
    public void setUsername(String username) {
        model.setUsername(username);
    }

    @Override
    public String getUsername() {
        return model.getUsername();
    }

    @Override
    public void setCategory(int c) {
        model.setCategory(c);
    }

    @Override
    public int getCategory() {
        return model.getCategory();
    }

    @Override
    public void setOrientation(String o) {
        model.setOrientation(o);
    }

    @Override
    public String getOrientation() {
        return model.getOrientation();
    }

    @Override
    public void setFeatured(boolean f) {
        model.setFeatured(f);
    }

    @Override
    public boolean isFeatured() {
        return model.isFeatured();
    }
}
