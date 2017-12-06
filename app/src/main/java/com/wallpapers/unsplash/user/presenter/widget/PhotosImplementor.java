package com.wallpapers.unsplash.user.presenter.widget;

import android.content.Context;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.interfaces.model.PhotosModel;
import com.wallpapers.unsplash.common.interfaces.presenter.PhotosPresenter;
import com.wallpapers.unsplash.common.interfaces.view.PhotosView;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;
import com.wallpapers.unsplash.common.utils.helper.NotificationHelper;
import com.wallpapers.unsplash.user.model.widget.PhotosObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Photos implementor.
 * */

public class PhotosImplementor
        implements PhotosPresenter {

    private PhotosModel model;
    private PhotosView view;

    private OnRequestPhotosListener listener;

    public PhotosImplementor(PhotosModel model, PhotosView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestPhotos(Context c, int page, boolean refresh, String query) {
        if (!model.isRefreshing() && !model.isLoading()) {
            if (refresh) {
                model.setRefreshing(true);
            } else {
                model.setLoading(true);
            }
            switch (model.getPhotosType()) {
                case PhotosObject.PHOTOS_TYPE_PHOTOS:
                    requestUserPhotos(c, page, refresh, model.getPhotosOrder());
                    break;

                case PhotosObject.PHOTOS_TYPE_LIKES:
                    requestUserLikes(c, page, refresh, model.getPhotosOrder());
                    break;
            }
        }
    }

    @Override
    public void cancelRequest() {
        if (listener != null) {
            listener.cancel();
        }
        model.getService().cancel();
        model.setRefreshing(false);
        model.setLoading(false);
    }

    @Override
    public void refreshNew(Context c, boolean notify, String query) {
        if (notify) {
            view.setRefreshing(true);
        }
        requestPhotos(c, model.getPhotosPage(), true, query);
    }

    @Override
    public void loadMore(Context c, boolean notify, String query) {
        if (notify) {
            view.setLoading(true);
        }
        requestPhotos(c, model.getPhotosPage(), false, query);
    }

    @Override
    public void initRefresh(Context c, String query) {
        cancelRequest();
        refreshNew(c, false, query);
        view.initRefreshStart();
    }

    @Override
    public boolean canLoadMore() {
        return !model.isRefreshing() && !model.isLoading() && !model.isOver();
    }

    @Override
    public boolean isRefreshing() {
        return model.isRefreshing();
    }

    @Override
    public boolean isLoading() {
        return model.isLoading();
    }

    @Override
    public Object getRequestKey() {
        return model.getRequestKey();
    }

    @Override
    public void setRequestKey(Object k) {
        model.setRequestKey(k);
    }

    @Override
    public int getPhotosType() {
        return model.getPhotosType();
    }

    @Override
    public String getPhotosOrder() {
        return model.getPhotosOrder();
    }

    @Override
    public void setOrder(String key) {
        model.setPhotosOrder(key);
    }

    @Override
    public String getOrder() {
        return model.getPhotosOrder();
    }

    @Override
    public void setPage(int page) {
        model.setPhotosPage(page);
    }

    @Override
    public void setPageList(List<Integer> pageList) {
        model.setPageList(pageList);
    }

    @Override
    public void setOver(boolean over) {
        model.setOver(over);
        view.setPermitLoading(!over);
    }

    @Override
    public void setActivityForAdapter(BaseActivity a) {
        model.getAdapter().setActivity(a);
    }

    @Override
    public PhotoAdapter getAdapter() {
        return model.getAdapter();
    }

    private void requestUserPhotos(Context c, int page, boolean refresh, String order) {
        page = Math.max(1, refresh ? 1 : page + 1);
        listener = new OnRequestPhotosListener(c, page, refresh);
        model.getService()
                .requestUserPhotos(
                        ((User) model.getRequestKey()).username,
                        page,
                        UnsplashApplication.DEFAULT_PER_PAGE,
                        order,
                        listener);
    }

    private void requestUserLikes(Context c, int page, boolean refresh, String order) {
        page = Math.max(1, refresh ? 1 : page + 1);
        listener = new OnRequestPhotosListener(c, page, refresh);
        model.getService()
                .requestUserLikes(
                        ((User) model.getRequestKey()).username,
                        page,
                        UnsplashApplication.DEFAULT_PER_PAGE,
                        order,
                        listener);
    }

    // interface.

    private class OnRequestPhotosListener implements PhotoService.OnRequestPhotosListener {
        // data
        private Context c;
        private int page;
        private boolean refresh;
        private boolean canceled;

        OnRequestPhotosListener(Context c, int page, boolean refresh) {
            this.c = c;
            this.page = page;
            this.refresh = refresh;
            this.canceled = false;
        }

        public void cancel() {
            canceled = true;
        }

        @Override
        public void onRequestPhotosSuccess(Call<List<Photo>> call, Response<List<Photo>> response) {
            if (canceled) {
                return;
            }
            model.setRefreshing(false);
            model.setLoading(false);
            if (refresh) {
                view.setRefreshing(false);
            } else {
                view.setLoading(false);
            }
            if (response.isSuccessful()) {
                model.setPhotosPage(page);
                if (refresh) {
                    model.getAdapter().clearItem();
                    setOver(false);
                }
                for (int i = 0; i < response.body().size(); i ++) {
                    model.getAdapter().insertItem(response.body().get(i));
                }
                if (response.body().size() < UnsplashApplication.DEFAULT_PER_PAGE) {
                    setOver(true);
                }
                view.requestPhotosSuccess();
            } else {
                view.requestPhotosFailed(c.getString(R.string.feedback_load_nothing_tv));
            }
        }

        @Override
        public void onRequestPhotosFailed(Call<List<Photo>> call, Throwable t) {
            if (canceled) {
                return;
            }
            model.setRefreshing(false);
            model.setLoading(false);
            if (refresh) {
                view.setRefreshing(false);
            } else {
                view.setLoading(false);
            }
            NotificationHelper.showSnackbar(
                    c.getString(R.string.feedback_load_failed_toast)
                            + " (" + t.getMessage() + ")");
            view.requestPhotosFailed(c.getString(R.string.feedback_load_failed_tv));
        }
    }
}
