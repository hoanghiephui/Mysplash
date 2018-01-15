package com.wallpaper.unsplash.me.presenter.activity;

import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.data.service.UserService;
import com.wallpaper.unsplash.common.interfaces.model.UserModel;
import com.wallpaper.unsplash.common.interfaces.presenter.UserPresenter;
import com.wallpaper.unsplash.common.interfaces.view.UserView;
import com.wallpaper.unsplash.common.utils.manager.AuthManager;

import retrofit2.Call;
import retrofit2.Response;

/**
 * User implementor.
 * */

public class UserImplementor
        implements UserPresenter {

    private UserModel model;
    private UserView view;

    private OnRequestUserProfileListener requestUserProfileListener;

    public UserImplementor(UserModel model, UserView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestUser() {
        requestUserProfileListener = new OnRequestUserProfileListener();
        model.getUserService().requestUserProfile(
                AuthManager.getInstance().getUsername(),
                requestUserProfileListener);
    }

    @Override
    public void followUser() {
        // do nothing.
    }

    @Override
    public void cancelFollowUser() {
        // do nothing.
    }

    @Override
    public void cancelRequest() {
        if (requestUserProfileListener != null) {
            requestUserProfileListener.cancel();
        }
        model.getUserService().cancel();
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
        // data
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
            if (response.isSuccessful() && response.body() != null) {
                model.setUser(response.body());
                view.drawUserInfo(response.body());
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
}