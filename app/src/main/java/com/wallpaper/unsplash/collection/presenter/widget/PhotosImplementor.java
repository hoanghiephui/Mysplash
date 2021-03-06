package com.wallpaper.unsplash.collection.presenter.widget;

import android.content.Context;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.unsplash.Collection;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.data.service.PhotoService;
import com.wallpaper.unsplash.common.interfaces.model.PhotosModel;
import com.wallpaper.unsplash.common.interfaces.presenter.PhotosPresenter;
import com.wallpaper.unsplash.common.interfaces.view.PhotosView;
import com.wallpaper.unsplash.common.ui.adapter.PhotoAdapter;
import com.wallpaper.unsplash.common.utils.helper.NotificationHelper;
import com.wallpaper.unsplash.collection.model.widget.PhotosObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Photos implementor.
 *
 * A {@link PhotosPresenter} for
 * {@link com.wallpaper.unsplash.collection.view.widget.CollectionPhotosView}.
 *
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

    // HTTP request.

    @Override
    public void requestPhotos(Context c, int page, boolean refresh, String query) {
        if (!model.isLoading() && !model.isRefreshing()) {
            if (refresh) {
                model.setRefreshing(true);
            } else {
                model.setLoading(true);
            }
            page = Math.max(1, refresh ? 1 : page + 1);
            switch (model.getPhotosType()) {
                case PhotosObject.PHOTOS_TYPE_NORMAL:
                    requestCollectionPhotos(c, (Collection) model.getRequestKey(), page, refresh);
                    break;

                case PhotosObject.PHOTOS_TYPE_CURATED:
                    requestCuratedCollectionPhotos(c, (Collection) model.getRequestKey(), page, refresh);
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

    // load data interface.

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

    // HTTP request.

    private void requestCollectionPhotos(Context context,
                                         Collection collection, int page, boolean refresh) {
        listener = new OnRequestPhotosListener(context, page, refresh);
        model.getService()
                .requestCollectionPhotos(
                        collection.id,
                        page,
                        UnsplashApplication.DEFAULT_PER_PAGE,
                        listener);
    }

    private void requestCuratedCollectionPhotos(Context context,
                                                Collection collection, int page, boolean refresh) {
        listener = new OnRequestPhotosListener(context, page, refresh);
        model.getService()
                .requestCuratedCollectionPhotos(
                        collection.id,
                        page,
                        UnsplashApplication.DEFAULT_PER_PAGE,
                        listener);
    }

    // interface.

    // on request photos listener.

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
