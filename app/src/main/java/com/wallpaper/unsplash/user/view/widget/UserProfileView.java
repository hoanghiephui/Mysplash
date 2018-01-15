package com.wallpaper.unsplash.user.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.interfaces.model.LoadModel;
import com.wallpaper.unsplash.common.interfaces.model.UserModel;
import com.wallpaper.unsplash.common.interfaces.presenter.LoadPresenter;
import com.wallpaper.unsplash.common.interfaces.presenter.UserPresenter;
import com.wallpaper.unsplash.common.interfaces.view.LoadView;
import com.wallpaper.unsplash.common.interfaces.view.UserView;
import com.wallpaper.unsplash.common.ui.adapter.MyPagerAdapter;
import com.wallpaper.unsplash.common.ui.widget.nestedScrollView.NestedScrollAppBarLayout;
import com.wallpaper.unsplash.common.ui.widget.rippleButton.RippleButton;
import com.wallpaper.unsplash.common.utils.AnimUtils;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.helper.NotificationHelper;
import com.wallpaper.unsplash.common.utils.manager.AuthManager;
import com.wallpaper.unsplash.common.utils.manager.ThemeManager;
import com.wallpaper.unsplash.user.model.widget.LoadObject;
import com.wallpaper.unsplash.user.presenter.widget.LoadImplementor;
import com.wallpaper.unsplash.user.model.widget.UserObject;
import com.wallpaper.unsplash.user.presenter.widget.UserImplementor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User profile view.
 *
 * This view is used to show user's profile.
 *
 * */

public class UserProfileView extends FrameLayout
        implements UserView, LoadView,
        RippleButton.OnSwitchListener {

    @BindView(R.id.container_user_profile_progressView)
    CircularProgressView progressView;

    @BindView(R.id.container_user_profile_profileContainer)
    RelativeLayout profileContainer;

    @BindView(R.id.container_user_profile_followBtn)
    RippleButton rippleButton;

    @BindView(R.id.container_user_profile_locationTxt)
    TextView locationTxt;

    @BindView(R.id.container_user_profile_bio)
    TextView bioTxt;

    private MyPagerAdapter adapter;

    private UserModel userModel;
    private UserPresenter userPresenter;

    private LoadModel loadModel;
    private LoadPresenter loadPresenter;

    private OnRequestUserListener listener;

    public UserProfileView(Context context) {
        super(context);
        this.initialize();
    }

    public UserProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
    }

    public UserProfileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize();
    }

    // init.

    @SuppressLint("InflateParams")
    private void initialize() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.container_user_profile, null);
        addView(v);

        ButterKnife.bind(this, this);
        initModel();
        initPresenter();
        initView();
    }

    // init.

    private void initModel() {
        this.userModel = new UserObject();
        this.loadModel = new LoadObject(LoadModel.LOADING_STATE);
    }

    private void initPresenter() {
        this.userPresenter = new UserImplementor(userModel, this);
        this.loadPresenter = new LoadImplementor(loadModel, this);
    }

    private void initView() {
        progressView.setVisibility(VISIBLE);

        if (AuthManager.getInstance().isAuthorized()) {
            rippleButton.setOnSwitchListener(this);
        } else {
            rippleButton.setVisibility(GONE);
        }

        profileContainer.setVisibility(GONE);

        DisplayUtils.setTypeface(getContext(), locationTxt);
        DisplayUtils.setTypeface(getContext(), bioTxt);

        ImageView locationIcon = ButterKnife.findById(this, R.id.container_user_profile_locationIcon);
        ThemeManager.setImageResource(
                locationIcon, R.drawable.ic_location_light, R.drawable.ic_location_dark);
    }

    // control.

    @Nullable
    private ViewParent getAppBarParent() {
        ViewParent parent = getParent();
        while (parent != null && !(parent instanceof NestedScrollAppBarLayout)) {
            parent = parent.getParent();
        }
        return parent;
    }

    public User getUser() {
        return userPresenter.getUser();
    }

    public void setUser(User user, MyPagerAdapter adapter) {
        this.adapter = adapter;
        userPresenter.setUser(user);
    }

    public String getUserPortfolio() {
        return userPresenter.getUser().portfolio_url;
    }

    // HTTP request.

    public void requestUserProfile() {
        userPresenter.requestUser();
    }

    public void cancelRequest() {
        userPresenter.cancelRequest();
    }

    // interface.

    // on request user listener.

    public interface OnRequestUserListener {
        void onRequestUserSucceed(User u);
    }

    public void setOnRequestUserListener(OnRequestUserListener l) {
        this.listener = l;
    }

    // on switch listener.

    @Override
    public void onSwitch(boolean switchTo) {
        if (switchTo) {
            userPresenter.followUser();
        } else {
            userPresenter.cancelFollowUser();
        }
    }

    // view.

    // user data view.

    @SuppressLint("SetTextI18n")
    @Override
    public void drawUserInfo(User u) {
        if (listener != null) {
            listener.onRequestUserSucceed(u);
        }

        ViewParent parent = getAppBarParent();
        if (parent != null) {
            TransitionManager.beginDelayedTransition((ViewGroup) parent);
        }

        rippleButton.forceSwitch(u.followed_by_user);

        if (!TextUtils.isEmpty(u.location)) {
            locationTxt.setText(u.location);
        } else {
            locationTxt.setText("Unknown");
        }

        if (!TextUtils.isEmpty(u.bio)) {
            bioTxt.setText(u.bio);
        } else {
            bioTxt.setVisibility(GONE);
        }

        List<String> titleList = new ArrayList<>();
        titleList.add(
                DisplayUtils.abridgeNumber(u.total_photos)
                        + " " + getResources().getStringArray(R.array.user_tabs)[0]);
        titleList.add(
                DisplayUtils.abridgeNumber(u.total_likes)
                        + " " + getResources().getStringArray(R.array.user_tabs)[1]);
        titleList.add(
                DisplayUtils.abridgeNumber(u.total_collections)
                        + " " + getResources().getStringArray(R.array.user_tabs)[2]);
        adapter.titleList = titleList;
        adapter.notifyDataSetChanged();

        loadPresenter.setNormalState();
    }

    @Override
    public void initRefreshStart() {
        loadPresenter.setLoadingState();
    }

    @Override
    public void followRequestSuccess(boolean follow) {
        User user = getUser();
        user.followed_by_user = follow;
        if (follow) {
            user.followers_count ++;
        } else {
            user.followers_count --;
        }
        setUser(user, adapter);
        rippleButton.setSwitchResult(true);
    }

    @Override
    public void followRequestFailed(boolean follow) {
        rippleButton.setSwitchResult(false);
        if (follow) {
            NotificationHelper.showSnackbar(getContext().getString(R.string.feedback_follow_failed));
        } else {
            NotificationHelper.showSnackbar(getContext().getString(R.string.feedback_cancel_follow_failed));
        }
    }

    @Override
    public Context getContexts() {
        return getContext();
    }

    // load view.

    @Override
    public void animShow(final View v) {
        AnimUtils.animShow(v);
    }

    @Override
    public void animHide(final View v) {
        AnimUtils.animHide(v);
    }

    @Override
    public void setLoadingState(@Nullable BaseActivity activity, int old) {
        animShow(progressView);
        animHide(profileContainer);
    }

    @Override
    public void setFailedState(@Nullable BaseActivity activity, int old) {
        // do nothing.
    }

    @Override
    public void setNormalState(@Nullable BaseActivity activity, int old) {
        animShow(profileContainer);
        animHide(progressView);
    }
}