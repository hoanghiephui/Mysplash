package com.wangdaye.mysplash.me.presenter.widget;

import com.wangdaye.mysplash.common.interfaces.model.PagerModel;
import com.wangdaye.mysplash.common.interfaces.presenter.PagerPresenter;
import com.wangdaye.mysplash.common.interfaces.view.PagerView;

/**
 * Pager implementor.
 * */

public class PagerImplementor
        implements PagerPresenter {

    private PagerModel model;
    private PagerView view;

    public PagerImplementor(PagerModel model, PagerView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public boolean checkNeedRefresh() {
        return view.checkNeedRefresh();
    }

    @Override
    public void refreshPager() {
        view.refreshPager();
    }

    @Override
    public int getIndex() {
        return model.getIndex();
    }

    @Override
    public boolean isSelected() {
        return model.isSelected();
    }

    @Override
    public void setSelected(boolean selected) {
        model.setSelected(selected);
    }
}
