package com.wallpaper.unsplash.user.presenter.widget;

import android.content.Context;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.unsplash.Collection;
import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.data.service.CollectionService;
import com.wallpaper.unsplash.common.interfaces.model.CollectionsModel;
import com.wallpaper.unsplash.common.interfaces.presenter.CollectionsPresenter;
import com.wallpaper.unsplash.common.interfaces.view.CollectionsView;
import com.wallpaper.unsplash.common.ui.adapter.CollectionAdapter;
import com.wallpaper.unsplash.common.utils.helper.NotificationHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Collections implementor.
 * */

public class CollectionsImplementor
        implements CollectionsPresenter {

    private CollectionsModel model;
    private CollectionsView view;

    private OnRequestCollectionsListener listener;

    public CollectionsImplementor(CollectionsModel model, CollectionsView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestCollections(Context c, int page, boolean refresh, String query) {
        if (!model.isRefreshing() && !model.isLoading()) {
            if (refresh) {
                model.setRefreshing(true);
            } else {
                model.setLoading(true);
            }
            page = Math.max(1, refresh ? 1 : page + 1);
            listener = new OnRequestCollectionsListener(c, page, refresh);
            model.getService()
                    .requestUserCollections(
                            ((User) model.getRequestKey()).username,
                            page,
                            UnsplashApplication.DEFAULT_PER_PAGE,
                            listener);
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
        requestCollections(c, model.getCollectionsPage(), true, query);
    }

    @Override
    public void loadMore(Context c, boolean notify, String query) {
        if (notify) {
            view.setLoading(true);
        }
        requestCollections(c, model.getCollectionsPage(), false, query);
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
    public void setType(int key) {
        // do nothing.
    }

    @Override
    public int getType() {
        return model.getCollectionsType();
    }

    @Override
    public void setPage(int page) {
        model.setCollectionsPage(page);
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
    public CollectionAdapter getAdapter() {
        return model.getAdapter();
    }

    // interface.

    private class OnRequestCollectionsListener implements CollectionService.OnRequestCollectionsListener {
        // data
        private Context c;
        private int page;
        private boolean refresh;
        private boolean canceled;

        OnRequestCollectionsListener(Context c, int page, boolean refresh) {
            this.c = c;
            this.page = page;
            this.refresh = refresh;
            this.canceled = false;
        }

        public void cancel() {
            canceled = true;
        }

        @Override
        public void onRequestCollectionsSuccess(Call<List<Collection>> call, Response<List<Collection>> response) {
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
                model.setCollectionsPage(page);
                if (refresh) {
                    model.getAdapter().clearItem();
                    setOver(false);
                }
                for (int i = 0; i < response.body().size(); i ++) {
                    model.getAdapter().insertItem(response.body().get(i), model.getAdapter().getRealItemCount());
                }
                if (response.body().size() < UnsplashApplication.DEFAULT_PER_PAGE) {
                    setOver(true);
                }
                view.requestCollectionsSuccess();
            } else {
                view.requestCollectionsFailed(c.getString(R.string.feedback_load_nothing_tv));
            }
        }

        @Override
        public void onRequestCollectionsFailed(Call<List<Collection>> call, Throwable t) {
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
            view.requestCollectionsFailed(c.getString(R.string.feedback_load_failed_tv));
        }
    }
}
