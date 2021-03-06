package com.wallpaper.unsplash.main.presenter.widget;

import android.content.Context;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.unsplash.NotificationResult;
import com.wallpaper.unsplash.common.interfaces.model.NotificationsModel;
import com.wallpaper.unsplash.common.interfaces.presenter.NotificationsPresenter;
import com.wallpaper.unsplash.common.interfaces.view.NotificationsView;
import com.wallpaper.unsplash.common.ui.adapter.NotificationAdapter;
import com.wallpaper.unsplash.common.utils.manager.AuthManager;
import com.wallpaper.unsplash.common.utils.manager.UserNotificationManager;

import java.util.List;

/**
 * Notifications implementor.
 *
 * */

public class NotificationsImplementor
        implements NotificationsPresenter,
        UserNotificationManager.OnUpdateNotificationListener {

    private Context context;
    private NotificationsModel model;
    private NotificationsView view;

    public NotificationsImplementor(Context context,
                                    NotificationsModel model, NotificationsView view) {
        this.context = context;
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestNotifications(Context c, boolean refresh) {
        if (refresh) {
            model.setRefreshing(true);
        } else {
            model.setLoading(true);
        }
        AuthManager.getInstance().requestPersonalNotifications();
    }

    @Override
    public void cancelRequest() {
        AuthManager.getInstance().getNotificationManager().cancelRequest(false);
    }

    @Override
    public void loadMore(Context c, boolean notify) {
        if (notify) {
            view.setLoading(true);
        }
        requestNotifications(c, false);
    }

    @Override
    public void initRefresh(Context c) {
        view.initRefreshStart();
        if (AuthManager.getInstance()
                .getNotificationManager()
                .getNotificationList().size() == 0
                &&
                !AuthManager.getInstance()
                        .getNotificationManager()
                        .isLoadFinish()) {
            requestNotifications(c, true);
        } else {
            model.setRefreshing(false);
            view.requestNotificationsSuccess();
        }
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
    public void setActivityForAdapter(BaseActivity a) {
        model.getAdapter().setActivity(a);
    }

    @Override
    public NotificationAdapter getAdapter() {
        return model.getAdapter();
    }

    // interface.

    // on update notifications listener.

    @Override
    public void onRequestNotificationSucceed(List<NotificationResult> resultList) {
        model.setRefreshing(false);
        view.setRefreshing(false);
        model.setLoading(false);
        view.setLoading(false);
        view.requestNotificationsSuccess();
    }

    @Override
    public void onRequestNotificationFailed() {
        model.setRefreshing(false);
        view.setRefreshing(false);
        model.setLoading(false);
        view.setLoading(false);
        view.requestNotificationsFailed(context.getString(R.string.feedback_load_failed_tv));
    }

    @Override
    public void onAddNotification(NotificationResult result, int position) {
        model.getAdapter().notifyItemInserted(position);
    }

    @Override
    public void onClearNotification() {
        model.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onSetLatestTime() {
        // do nothing.
    }
}
