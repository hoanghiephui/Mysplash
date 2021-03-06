package com.wallpaper.unsplash.user.presenter.activity;

import android.net.Uri;

import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.data.service.UserService;
import com.wallpaper.unsplash.common.interfaces.model.BrowsableModel;
import com.wallpaper.unsplash.common.interfaces.presenter.BrowsablePresenter;
import com.wallpaper.unsplash.common.interfaces.view.BrowsableView;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Browsable implementor.
 * */

public class BrowsableImplementor
        implements BrowsablePresenter,
        UserService.OnRequestUserProfileListener {

    private BrowsableModel model;
    private BrowsableView view;

    public BrowsableImplementor(BrowsableModel model, BrowsableView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public Uri getIntentUri() {
        return model.getIntentUri();
    }

    @Override
    public boolean isBrowsable() {
        return model.isBrowsable();
    }

    @Override
    public void requestBrowsableData() {
        view.showRequestDialog();
        requestUser();
    }

    @Override
    public void visitPreviousPage() {
        view.visitPreviousPage();
    }

    @Override
    public void cancelRequest() {
        ((UserService) model.getService()).cancel();
    }

    private void requestUser() {
        ((UserService) model.getService()).requestUserProfile(
                model.getBrowsableDataKey().get(0).substring(1),
                this);
    }

    // interface.

    @Override
    public void onRequestUserProfileSuccess(Call<User> call, Response<User> response) {
        if (response.isSuccessful() && response.body() != null) {
            view.dismissRequestDialog();
            view.drawBrowsableView(response.body());
        } else {
            requestUser();
        }
    }

    @Override
    public void onRequestUserProfileFailed(Call<User> call, Throwable t) {
        requestUser();
    }
}
