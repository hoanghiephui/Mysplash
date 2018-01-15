package com.wallpaper.unsplash.common.interfaces.view;

/**
 * Multi-filter view.
 *
 * A view which can request {@link com.wallpaper.unsplash.common.data.entity.unsplash.Photo} random
 * by multiple params and show them.
 *
 * */

public interface MultiFilterView {

    void setRefreshing(boolean refreshing);
    void setLoading(boolean loading);

    void setPermitRefreshing(boolean permit);
    void setPermitLoading(boolean permit);

    void initRefreshStart();
    void requestPhotosSuccess();
    void requestPhotosFailed(String feedback);
}