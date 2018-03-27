package com.wallpapers.unsplash.user.presenter.widget;

import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.data.service.FeedService;
import com.wallpapers.unsplash.common.data.service.UserService;
import com.wallpapers.unsplash.common.interfaces.model.UserModel;
import com.wallpapers.unsplash.common.interfaces.presenter.UserPresenter;
import com.wallpapers.unsplash.common.interfaces.view.UserView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * User implementor.
 */

public class UserImplementor
        implements UserPresenter {

    private UserModel model;
    private UserView view;

    private OnRequestUserProfileListener requestUserProfileListener;
    private OnFollowListener followListener;

    public UserImplementor(UserModel model, UserView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestUser() {
        view.initRefreshStart();
        requestUserProfileListener = new OnRequestUserProfileListener();
        model.getUserService().requestUserProfile(model.getUser().username, requestUserProfileListener);
    }

    @Override
    public void followUser() {
        followListener = new OnFollowListener();
        model.getFeedService().setFollowUser(view.getContexts(), model.getUser().username, true, followListener);
    }

    @Override
    public void cancelFollowUser() {
        followListener = new OnFollowListener();
        model.getFeedService().setFollowUser(view.getContexts(), model.getUser().username, true, followListener);
    }

    @Override
    public void cancelRequest() {
        if (requestUserProfileListener != null) {
            requestUserProfileListener.cancel();
        }
        if (followListener != null) {
            followListener.cancel();
        }
        model.getUserService().cancel();
        // model.getFeedService().cancel();
    }

    @Override
    public void setUser(User u) {
        model.setUser(u);
    }

    @Override
    public User getUser() {
        return model.getUser();
    }

    // interface.

    // on request user profile swipeListener.

    private class OnRequestUserProfileListener implements UserService.OnRequestUserProfileListener {

        private boolean canceled;

        OnRequestUserProfileListener() {
            canceled = false;
        }

        public void cancel() {
            canceled = true;
        }

        @Override
        public void onRequestUserProfileSuccess(Call<User> call, Response<User> response) {
            if (canceled) {
                return;
            }
            if (response.isSuccessful()) {
                User user = response.body();
                if (user != null) {
                    user.complete = true;
                    model.setUser(user);
                    view.drawUserInfo(user);
                }

            } else {
                requestUser();
            }
        }

        @Override
        public void onRequestUserProfileFailed(Call<User> call, Throwable t) {
            if (canceled) {
                return;
            }
            requestUser();
        }
    }

    // on follow swipeListener.

    private class OnFollowListener implements FeedService.OnFollowListener {

        private boolean canceled;

        OnFollowListener() {
            canceled = false;
        }

        public void cancel() {
            canceled = true;
        }

        @Override
        public void onFollowSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (canceled) {
                return;
            }
            if (response.isSuccessful()) {
                view.followRequestSuccess(true);
            } else {
                view.followRequestFailed(true);
            }
        }

        @Override
        public void onCancelFollowSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (canceled) {
                return;
            }
            if (response.isSuccessful()) {
                view.followRequestSuccess(false);
            } else {
                view.followRequestFailed(false);
            }
        }

        @Override
        public void onFollowFailed(Call<ResponseBody> call, Throwable t) {
            if (canceled) {
                return;
            }
            view.followRequestFailed(true);
        }

        @Override
        public void onCancelFollowFailed(Call<ResponseBody> call, Throwable t) {
            view.followRequestFailed(false);
        }
    }

}