package com.wangdaye.mysplash.main.presenter.widget;

import android.content.Context;

import com.wangdaye.mysplash.Mysplash;
import com.wangdaye.mysplash.R;
import com.wangdaye.mysplash._common.data.api.PhotoApi;
import com.wangdaye.mysplash._common.data.data.Photo;
import com.wangdaye.mysplash._common.data.service.PhotoService;
import com.wangdaye.mysplash._common.i.model.PhotosModel;
import com.wangdaye.mysplash._common.i.presenter.PhotosPresenter;
import com.wangdaye.mysplash._common.ui.toast.MaterialToast;
import com.wangdaye.mysplash._common.utils.ValueUtils;
import com.wangdaye.mysplash._common.i.view.PhotosView;
import com.wangdaye.mysplash.main.model.widget.PhotosObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Photos implementor.
 * */

public class PhotosImplementor
        implements PhotosPresenter {
    // model & view.
    private PhotosModel model;
    private PhotosView view;

    /** <br> life cycle. */

    public PhotosImplementor(PhotosModel model, PhotosView view) {
        this.model = model;
        this.view = view;
    }

    /** <br> presenter. */

    @Override
    public void requestPhotos(Context c, int page, boolean refresh) {
        if (!model.isLoading()) {
            model.setLoading(true);
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
            }
        }
    }

    @Override
    public void cancelRequest() {
        model.getService().cancel();
        model.getAdapter().cancelService();
    }

    @Override
    public void refreshNew(Context c, boolean notify) {
        if (notify) {
            view.setRefreshing(true);
        }
        requestPhotos(c, model.getPhotosPage(), true);
    }

    @Override
    public void loadMore(Context c, boolean notify) {
        if (notify) {
            view.setLoading(true);
        }
        requestPhotos(c, model.getPhotosPage(), false);
    }

    @Override
    public void initRefresh(Context c) {
        model.getService().cancel();
        model.setLoading(false);
        refreshNew(c, false);
        view.initRefreshStart();
    }

    @Override
    public boolean waitingRefresh() {
        return !model.isLoading() && model.getAdapter().getRealItemCount() <= 0;
    }

    @Override
    public boolean canLoadMore() {
        return !model.isLoading() && !model.isOver();
    }

    @Override
    public void setOrder(String key) {
        model.setPhotosOrder(key);
    }

    /** <br> utils. */

    private void requestNewPhotosOrders(Context c, int page, boolean refresh) {
        page = refresh ? 1: page + 1;
        model.getService()
                .requestPhotos(
                        page,
                        Mysplash.DEFAULT_PER_PAGE,
                        model.getPhotosOrder(),
                        new OnRequestPhotosListener(c, page, Mysplash.CATEGORY_TOTAL_NEW, refresh, false));
    }

    private void requestNewPhotosRandom(Context c, int page, boolean refresh) {
        if (refresh) {
            page = 0;
            model.setPageList(ValueUtils.getPageListByCategory(Mysplash.CATEGORY_TOTAL_NEW));
        }
        model.getService()
                .requestPhotos(
                        model.getPageList().get(page),
                        Mysplash.DEFAULT_PER_PAGE,
                        PhotoApi.ORDER_BY_LATEST,
                        new OnRequestPhotosListener(c, page, Mysplash.CATEGORY_TOTAL_NEW, refresh, true));
    }

    private void requestFeaturePhotosOrders(Context c, int page, boolean refresh) {
        page = refresh ? 1 : page + 1;
        model.getService()
                .requestCuratePhotos(
                        page,
                        Mysplash.DEFAULT_PER_PAGE,
                        model.getPhotosOrder(),
                        new OnRequestPhotosListener(c, page, Mysplash.CATEGORY_TOTAL_FEATURED, refresh, false));
    }

    private void requestFeaturePhotosRandom(Context c, int page, boolean refresh) {
        if (refresh) {
            page = 0;
            model.setPageList(ValueUtils.getPageListByCategory(Mysplash.CATEGORY_TOTAL_FEATURED));
        }
        model.getService()
                .requestCuratePhotos(
                        model.getPageList().get(page),
                        Mysplash.DEFAULT_PER_PAGE,
                        PhotoApi.ORDER_BY_LATEST,
                        new OnRequestPhotosListener(c, page, Mysplash.CATEGORY_TOTAL_FEATURED, refresh, true));
    }

    /** <br> interface. */

    private class OnRequestPhotosListener implements PhotoService.OnRequestPhotosListener {
        // data
        private Context c;
        private int page;
        private int category;
        private boolean refresh;
        private boolean random;

        public OnRequestPhotosListener(Context c, int page, int category, boolean refresh, boolean random) {
            this.c = c;
            this.page = page;
            this.category = category;
            this.refresh = refresh;
            this.random = random;
        }

        @Override
        public void onRequestPhotosSuccess(Call<List<Photo>> call, Response<List<Photo>> response) {
            model.setLoading(false);
            if (refresh) {
                model.getAdapter().clearItem();
                model.setOver(false);
                view.setRefreshing(false);
                view.setPermitLoading(true);
            } else {
                view.setLoading(false);
            }
            if (response.isSuccessful()
                    && model.getAdapter().getRealItemCount() + response.body().size() > 0) {
                ValueUtils.writePhotoCount(c, response, category);
                if (random) {
                    model.setPhotosPage(page + 1);
                } else {
                    model.setPhotosPage(page);
                }
                for (int i = 0; i < response.body().size(); i ++) {
                    model.getAdapter().insertItem(response.body().get(i));
                }
                if (response.body().size() < Mysplash.DEFAULT_PER_PAGE) {
                    model.setOver(true);
                    view.setPermitLoading(false);
                    if (response.body().size() == 0) {
                        MaterialToast.makeText(
                                c,
                                c.getString(R.string.feedback_is_over),
                                null,
                                MaterialToast.LENGTH_SHORT).show();
                    }
                }
                view.requestPhotosSuccess();
            } else {
                view.requestPhotosFailed(c.getString(R.string.feedback_load_nothing_tv));
            }
        }

        @Override
        public void onRequestPhotosFailed(Call<List<Photo>> call, Throwable t) {
            model.setLoading(false);
            if (refresh) {
                view.setRefreshing(false);
            } else {
                view.setLoading(false);
            }
            MaterialToast.makeText(
                    c,
                    c.getString(R.string.feedback_load_failed_toast) + " (" + t.getMessage() + ")",
                    null,
                    MaterialToast.LENGTH_SHORT).show();
            view.requestPhotosFailed(c.getString(R.string.feedback_load_failed_tv));
        }
    }
}
