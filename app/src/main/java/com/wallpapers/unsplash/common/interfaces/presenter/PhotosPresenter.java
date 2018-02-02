package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;

import java.util.List;

/**
 * Photos presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.PhotosView}.
 */

public interface PhotosPresenter {

    // HTTP request.

    void requestPhotos(Context c, @UnsplashApplication.PageRule int page, boolean refresh, String query);

    void cancelRequest();

    // load data interface.

    /**
     * The param notify is used to control the SwipeRefreshLayout. If set true, the
     * SwipeRefreshLayout will show the refresh animation.
     */
    void refreshNew(Context c, boolean notify, String query);

    void loadMore(Context c, boolean notify, String query);

    void initRefresh(Context c, String query);

    boolean canLoadMore();

    boolean isRefreshing();

    boolean isLoading();

    // manage HTTP request parameters.

    Object getRequestKey();

    void setRequestKey(Object k);

    int getPhotosType();

    String getPhotosOrder();

    void setOrder(String key);

    String getOrder();

    void setPage(@UnsplashApplication.PageRule int page);

    void setPageList(List<Integer> pageList);

    void setOver(boolean over);

    void setActivityForAdapter(BaseActivity a);

    PhotoAdapter getAdapter();
}
