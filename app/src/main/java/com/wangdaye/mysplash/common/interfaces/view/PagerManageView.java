package com.wangdaye.mysplash.common.interfaces.view;

/**
 * Pager manage view.
 *
 * A view which can manage {@link PagerView}.
 *
 * */

public interface PagerManageView {

    int getPagerItemCount(int position);

    PagerView getPagerView(int position);

    boolean canPagerSwipeBack(int position, int dir);
}
