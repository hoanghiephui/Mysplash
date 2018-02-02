package com.wallpapers.unsplash.main.presenter.widget;

import android.content.Context;
import android.text.TextUtils;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.data.entity.unsplash.TrendingFeed;
import com.wallpapers.unsplash.common.data.service.FeedService;
import com.wallpapers.unsplash.common.interfaces.model.TrendingModel;
import com.wallpapers.unsplash.common.interfaces.presenter.TrendingPresenter;
import com.wallpapers.unsplash.common.interfaces.view.TrendingView;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;
import com.wallpapers.unsplash.common.utils.helper.NotificationHelper;

import retrofit2.Call;

/**
 * Trending implementor.
 */

public class TrendingImplementor implements TrendingPresenter {

    private TrendingModel model;
    private TrendingView view;

    private OnRequestTrendingFeedListener listener;

    public TrendingImplementor(TrendingModel model, TrendingView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestTrendingFeed(Context c, boolean refresh) {
        if (!model.isRefreshing() && !model.isLoading()) {
            if (refresh) {
                model.setRefreshing(true);
            } else {
                model.setLoading(true);
            }
            requestTrendingFeed(c, model.getNextPage(), refresh);
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
    public void refreshNew(Context c, boolean notify) {
        if (notify) {
            view.setRefreshing(true);
        }
        requestTrendingFeed(c, true);
    }

    @Override
    public void loadMore(Context c, boolean notify) {
        if (notify) {
            view.setLoading(true);
        }
        requestTrendingFeed(c, false);
    }

    @Override
    public void initRefresh(Context c) {
        cancelRequest();
        refreshNew(c, false);
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
    public void setNextPage(String nextPage) {
        model.setNextPage(nextPage);
    }

    @Override
    public String getNextPage() {
        return model.getNextPage();
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
    public int getAdapterItemCount() {
        return model.getAdapter().getRealItemCount();
    }

    @Override
    public PhotoAdapter getAdapter() {
        return model.getAdapter();
    }

    private void requestTrendingFeed(Context context, String nextPage, boolean refresh) {
        listener = new OnRequestTrendingFeedListener(context, refresh);
        model.getService()
                .requestTrendingFeed(context,
                        refresh ? model.getFirstPage() : nextPage,
                        listener);
    }

    // interface.

    private class OnRequestTrendingFeedListener implements FeedService.OnRequestTrendingFeedListener {

        private Context c;
        private boolean refresh;
        private boolean canceled;

        OnRequestTrendingFeedListener(Context c, boolean refresh) {
            this.c = c;
            this.refresh = refresh;
            this.canceled = false;
        }

        public void cancel() {
            canceled = true;
        }

        @Override
        public void onRequestTrendingFeedSuccess(Call<TrendingFeed> call, retrofit2.Response<TrendingFeed> response) {
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
                    && model.getAdapter().getRealItemCount() + response.body().photos.size() > 0) {
                if (refresh) {
                    model.getAdapter().clearItem();
                    setOver(false);
                }
                for (int i = 0; i < response.body().photos.size(); i++) {
                    model.getAdapter().insertItem(response.body().photos.get(i));
                }
                if (TextUtils.isEmpty(response.body().next_page)) {
                    setOver(true);
                }
                model.setNextPage(response.body().next_page);
                view.requestTrendingFeedSuccess();
            } else {
                view.requestTrendingFeedFailed(c.getString(R.string.feedback_load_nothing_tv));
            }
        }

        @Override
        public void onRequestTrendingFeedFailed(Call<TrendingFeed> call, Throwable t) {
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
            view.requestTrendingFeedFailed(c.getString(R.string.feedback_load_failed_tv));
        }
    }
}
