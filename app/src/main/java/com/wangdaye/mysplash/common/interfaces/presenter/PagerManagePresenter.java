package com.wangdaye.mysplash.common.interfaces.presenter;

import com.wangdaye.mysplash.common._basic.activity.MysplashActivity;
import com.wangdaye.mysplash.common.interfaces.view.PagerView;

/**
 * Pager manage presenter.
 *
 * Presenter for {@link com.wangdaye.mysplash.common.interfaces.view.PagerManageView}.
 *
 * */

public interface PagerManagePresenter {

    int getPagerPosition();
    void setPagerPosition(int position);

    int getPagerItemCount();

    PagerView getPagerView(int position);

    /**
     * Get the key word of the {@link PagerView}, like the order of photos, or type of collections.
     *
     * @return Key words.
     * */
    String getPagerKey(int position);

    void checkToRefresh(int position);

    /**
     * {@link MysplashActivity#backToTop()}
     * {@link com.wangdaye.mysplash.common.utils.BackToTopUtils}
     * */
    boolean needPagerBackToTop();
    void pagerScrollToTop();

    /**
     * Check {@link PagerView} can swipe back.
     *
     * @return Can swipe back.
     * */
    boolean canPagerSwipeBack(int dir);
}
