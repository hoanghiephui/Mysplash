package com.wallpapers.unsplash.main.presenter.widget;

import android.content.Context;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.data.api.PhotoApi;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photos;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.interfaces.model.PhotosModel;
import com.wallpapers.unsplash.common.interfaces.presenter.PhotosPresenter;
import com.wallpapers.unsplash.common.interfaces.view.PhotosView;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;
import com.wallpapers.unsplash.common.utils.ValueUtils;
import com.wallpapers.unsplash.common.utils.helper.NotificationHelper;
import com.wallpapers.unsplash.main.model.widget.PhotosObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Photos implementor.
 */

public class PhotosImplementor
        implements PhotosPresenter {

    private PhotosModel model;
    private PhotosView view;

    private OnRequestPhotosListener listener;
    private OnRequestPhotosByTagListen listenerByTag;

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
                case PhotosObject.PHOTOS_TYPE_NEW:
                    if (model.isRandomType()) {
                        requestNewPhotosRandom(c, page, refresh);
                    } else {
                        requestNewPhotosOrders(c, page, refresh);
                    }
                    break;

                case PhotosObject.PHOTOS_TYPE_FEATURED:
                    if (model.isRandomType()) {
                        requestFeaturePhotosRandom(c, page, refresh);
                    } else {
                        requestFeaturePhotosOrders(c, page, refresh);
                    }
                    break;
                case PhotosObject.PHOTOS_TYPE_TAG:
                    requestPhotoByTag(c, query, page, refresh);
                    break;
            }
        }
    }

    @Override
    public void cancelRequest() {
        if (listener != null) {
            listener.cancel();
        }
        if (listenerByTag != null) {
            listenerByTag.cancel();
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
        return null;
    }

    @Override
    public void setRequestKey(Object k) {
        // do nothing.
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

    private void requestNewPhotosOrders(Context c, int page, boolean refresh) {
        page = Math.max(1, refresh ? 1 : page + 1);
        listener = new OnRequestPhotosListener(c, page, refresh, false);
        model.getService()
                .requestPhotos(
                        page,
                        UnsplashApplication.DEFAULT_PER_PAGE,
                        model.getPhotosOrder(),
                        listener);
    }

    private void requestNewPhotosRandom(Context c, int page, boolean refresh) {
        if (refresh) {
            page = 0;
            model.setPageList(ValueUtils.getPageListByCategory(UnsplashApplication.CATEGORY_TOTAL_NEW));
        }
        listener = new OnRequestPhotosListener(c, page, refresh, true);
        model.getService()
                .requestPhotos(
                        model.getPageList().get(page),
                        UnsplashApplication.DEFAULT_PER_PAGE,
                        PhotoApi.ORDER_BY_LATEST,
                        listener);
    }

    private void requestFeaturePhotosOrders(Context c, int page, boolean refresh) {
        page = Math.max(1, refresh ? 1 : page + 1);
        listener = new OnRequestPhotosListener(c, page, refresh, false);
        model.getService()
                .requestCuratePhotos(
                        page,
                        UnsplashApplication.DEFAULT_PER_PAGE,
                        model.getPhotosOrder(),
                        listener);
    }

    private void requestFeaturePhotosRandom(Context c, int page, boolean refresh) {
        if (refresh) {
            page = 0;
            model.setPageList(ValueUtils.getPageListByCategory(UnsplashApplication.CATEGORY_TOTAL_FEATURED));
        }
        listener = new OnRequestPhotosListener(c, page, refresh, true);
        model.getService()
                .requestCuratePhotos(
                        model.getPageList().get(page),
                        UnsplashApplication.DEFAULT_PER_PAGE,
                        PhotoApi.ORDER_BY_LATEST,
                        listener);
    }

    private void requestPhotoByTag(Context context, String query, int page, boolean refresh) {
        page = Math.max(1, refresh ? 1 : page + 1);
        if (refresh) {
            model.setPageList(ValueUtils.getPageListByCategory(UnsplashApplication.CATEGORY_PHOTO_TAG));
        }
        listenerByTag = new OnRequestPhotosByTagListen(context, page, refresh, false);
        model.getService().requestPhotoByQuery(query, page, UnsplashApplication.DEFAULT_PER_PAGE, listenerByTag);
    }

    // interface.

    private class OnRequestPhotosByTagListen implements PhotoService.OnRequestPhotoByQueryListener {
        // data
        private Context c;
        private int page;
        private boolean refresh;
        private boolean random;
        private boolean canceled;

        OnRequestPhotosByTagListen(Context c, int page, boolean refresh, boolean random) {
            this.c = c;
            this.page = page;
            this.refresh = refresh;
            this.random = random;
            this.canceled = false;
        }

        public void cancel() {
            canceled = true;
        }

        @Override
        public void onRequestPhotosSuccesss(Call<Photos> call, Response<Photos> response) {
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
            if (response.isSuccessful()
                    && model.getAdapter().getRealItemCount() + response.body().getResults().size() > 0) {
                if (random) {
                    model.setPhotosPage(page + 1);
                } else {
                    model.setPhotosPage(page);
                }
                if (refresh) {
                    model.getAdapter().clearItem();
                    setOver(false);
                }
                for (int i = 0; i < response.body().getResults().size(); i++) {
                    model.getAdapter().insertItem(response.body().getResults().get(i));
                }
                if (response.body().getResults().size() < UnsplashApplication.DEFAULT_PER_PAGE) {
                    setOver(true);
                }
                view.requestPhotosSuccess();
            } else {
                view.requestPhotosFailed(c.getString(R.string.feedback_load_nothing_tv));
            }
        }

        @Override
        public void onRequestPhotosFailedd(Call<Photos> call, Throwable t) {
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

    private class OnRequestPhotosListener implements PhotoService.OnRequestPhotosListener {
        // data
        private Context c;
        private int page;
        private boolean refresh;
        private boolean random;
        private boolean canceled;

        OnRequestPhotosListener(Context c, int page, boolean refresh, boolean random) {
            this.c = c;
            this.page = page;
            this.refresh = refresh;
            this.random = random;
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
            if (response.isSuccessful()
                    && model.getAdapter().getRealItemCount() + response.body().size() > 0) {
                if (random) {
                    model.setPhotosPage(page + 1);
                } else {
                    model.setPhotosPage(page);
                }
                if (refresh) {
                    model.getAdapter().clearItem();
                    setOver(false);
                }
                for (int i = 0; i < response.body().size(); i++) {
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
