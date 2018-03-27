package com.wallpapers.unsplash.user.presenter.widget;

import com.wallpapers.unsplash.common.interfaces.model.ScrollModel;
import com.wallpapers.unsplash.common.interfaces.presenter.ScrollPresenter;
import com.wallpapers.unsplash.common.interfaces.view.ScrollView;

/**
 * Scroll implementor.
 */

public class ScrollImplementor
        implements ScrollPresenter {

    private ScrollModel model;
    private ScrollView view;

    public ScrollImplementor(ScrollModel model, ScrollView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public boolean isToTop() {
        return model.isToTop();
    }

    @Override
    public void setToTop(boolean top) {
        model.setToTop(top);
    }

    @Override
    public void scrollToTop() {
        view.scrollToTop();
    }

    @Override
    public void autoLoad(int dy) {
        view.autoLoad(dy);
    }

    @Override
    public boolean needBackToTop() {
        return view.needBackToTop();
    }
}