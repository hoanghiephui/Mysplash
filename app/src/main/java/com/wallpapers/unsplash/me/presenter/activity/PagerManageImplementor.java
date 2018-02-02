package com.wallpapers.unsplash.me.presenter.activity;

import com.wallpapers.unsplash.common.interfaces.model.PagerManageModel;
import com.wallpapers.unsplash.common.interfaces.presenter.PagerManagePresenter;
import com.wallpapers.unsplash.common.interfaces.view.PagerManageView;
import com.wallpapers.unsplash.common.interfaces.view.PagerView;

/**
 * Pager manage implementor.
 */

public class PagerManageImplementor
        implements PagerManagePresenter {

    private PagerManageModel model;
    private PagerManageView view;

    public PagerManageImplementor(PagerManageModel model, PagerManageView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public int getPagerPosition() {
        return model.getPagerPosition();
    }

    @Override
    public void setPagerPosition(int position) {
        model.setPagerPosition(position);
    }

    @Override
    public PagerView getPagerView(int position) {
        return view.getPagerView(position);
    }

    @Override
    public void checkToRefresh(int position) {
        getPagerView(position).checkToRefresh();
    }

    @Override
    public boolean needPagerBackToTop() {
        return view.getPagerView(model.getPagerPosition()).checkNeedBackToTop();
    }

    @Override
    public void pagerScrollToTop() {
        getPagerView(model.getPagerPosition()).scrollToPageTop();
    }

    @Override
    public String getPagerKey(int position) {
        return getPagerView(position).getKey();
    }

    @Override
    public boolean canPagerSwipeBack(int dir) {
        return view.canPagerSwipeBack(model.getPagerPosition(), dir);
    }

    @Override
    public int getPagerItemCount() {
        return view.getPagerItemCount(model.getPagerPosition());
    }
}
