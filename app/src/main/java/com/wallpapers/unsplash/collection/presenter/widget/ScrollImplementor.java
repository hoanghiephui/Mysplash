package com.wallpapers.unsplash.collection.presenter.widget;

import com.wallpapers.unsplash.common.interfaces.model.ScrollModel;
import com.wallpapers.unsplash.common.interfaces.presenter.ScrollPresenter;
import com.wallpapers.unsplash.common.interfaces.view.ScrollView;

/**
 * Scroll implementor.
 * <p>
 * A {@link ScrollPresenter} for
 * {@link com.wallpapers.unsplash.collection.view.widget.CollectionPhotosView}.
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
    public boolean needBackToTop() {
        return view.needBackToTop();
    }

    @Override
    public void scrollToTop() {
        model.setToTop(true);
        view.scrollToTop();
    }

    @Override
    public void autoLoad(int dy) {
        view.autoLoad(dy);
    }
}
