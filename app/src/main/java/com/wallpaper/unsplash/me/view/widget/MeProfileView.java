package com.wallpaper.unsplash.me.view.widget;

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
import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.unsplash.Me;
import com.wallpaper.unsplash.common.ui.widget.nestedScrollView.NestedScrollAppBarLayout;
import com.wallpaper.unsplash.common.ui.widget.rippleButton.RippleButton;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.common.utils.manager.AuthManager;
import com.wallpaper.unsplash.common.interfaces.model.LoadModel;
import com.wallpaper.unsplash.common.interfaces.presenter.LoadPresenter;
import com.wallpaper.unsplash.common.interfaces.view.LoadView;
import com.wallpaper.unsplash.common.utils.AnimUtils;
import com.wallpaper.unsplash.common.utils.manager.ThemeManager;
import com.wallpaper.unsplash.me.model.widget.LoadObject;
import com.wallpaper.unsplash.me.presenter.widget.LoadImplementor;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Me profile view.
 *
 * This view is used to show application's profile.
 *
 * */

public class MeProfileView  extends FrameLayout
        implements LoadView,
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

    private LoadModel loadModel;
    private LoadPresenter loadPresenter;

    public MeProfileView(Context context) {
        super(context);
        this.initialize();
    }

    public MeProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
    }

    public MeProfileView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        this.loadModel = new LoadObject(LoadModel.LOADING_STATE);
    }

    private void initPresenter() {
        this.loadPresenter = new LoadImplementor(loadModel, this);
    }

    private void initView() {
        progressView.setVisibility(VISIBLE);

        rippleButton.setDontAnimate(true);
        rippleButton.setOnSwitchListener(this);

        profileContainer.setVisibility(GONE);

        DisplayUtils.setTypeface(getContext(), locationTxt);
        DisplayUtils.setTypeface(getContext(), bioTxt);

        ImageView locationIcon = ButterKnife.findById(this, R.id.container_user_profile_locationIcon);
        ThemeManager.setImageResource(
                locationIcon, R.drawable.ic_location_light, R.drawable.ic_location_dark);
    }

    // control.

    @SuppressLint("SetTextI18n")
    public void drawMeProfile(Me me) {
        ViewParent parent = getAppBarParent();
        if (parent != null && parent instanceof NestedScrollAppBarLayout) {
            TransitionManager.beginDelayedTransition((ViewGroup) parent);
        }

        rippleButton.forceSwitch(true);
        rippleButton.setButtonTitles(
                new String[] {
                        getContext().getString(R.string.my_follow).toUpperCase(),
                        getContext().getString(R.string.my_follow).toUpperCase()});

        if (!TextUtils.isEmpty(me.location)) {
            locationTxt.setText(me.location);
        } else {
            locationTxt.setText("Unknown");
        }

        if (!TextUtils.isEmpty(me.bio)) {
            bioTxt.setText(me.bio);
        } else {
            bioTxt.setVisibility(GONE);
        }

        loadPresenter.setNormalState();
    }

    @Nullable
    private ViewParent getAppBarParent() {
        ViewParent parent = getParent();
        while (parent != null && !(parent instanceof NestedScrollAppBarLayout)) {
            parent = parent.getParent();
        }
        return parent;
    }

    public void setLoading() {
        loadPresenter.setLoadingState();
    }

    // interface.

    // on switch listener.

    @Override
    public void onSwitch(boolean switchTo) {
        if (AuthManager.getInstance().isAuthorized()) {
            IntentHelper.startMyFollowActivity(UnsplashApplication.getInstance().getTopActivity());
        }
    }

    // view.

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
