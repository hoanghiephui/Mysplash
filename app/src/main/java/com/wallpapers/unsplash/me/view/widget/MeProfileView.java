package com.wallpapers.unsplash.me.view.widget;

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
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.data.entity.unsplash.Tag;
import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.interfaces.model.LoadModel;
import com.wallpapers.unsplash.common.interfaces.presenter.LoadPresenter;
import com.wallpapers.unsplash.common.interfaces.view.LoadView;
import com.wallpapers.unsplash.common.ui.widget.nestedScrollView.NestedScrollAppBarLayout;
import com.wallpapers.unsplash.common.ui.widget.rippleButton.RippleButton;
import com.wallpapers.unsplash.common.utils.AnimUtils;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;
import com.wallpapers.unsplash.common.utils.manager.ThemeManager;
import com.wallpapers.unsplash.me.model.widget.LoadObject;
import com.wallpapers.unsplash.me.presenter.widget.LoadImplementor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Me profile view.
 * <p>
 * This view is used to show application's profile.
 */

public class MeProfileView extends FrameLayout
        implements LoadView,
        RippleButton.OnSwitchListener {

    @BindView(R.id.container_user_profile_progressView)
    CircularProgressView progressView;

    @BindView(R.id.container_user_profile_profileContainer)
    RelativeLayout profileContainer;
    @BindView(R.id.container_user_profile_locationTxt)
    TextView locationTxt;
    @BindView(R.id.container_user_profile_locationContainer)
    RelativeLayout locationCon;

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
    private void initialize() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_me_profile, this, false);
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
        profileContainer.setVisibility(GONE);

        DisplayUtils.setTypeface(getContext(), locationTxt);
        DisplayUtils.setTypeface(getContext(), bioTxt);

        ImageView locationIcon = findViewById(R.id.container_user_profile_locationIcon);
        ThemeManager.setImageResource(
                locationIcon, R.drawable.ic_location_light, R.drawable.ic_location_dark);
    }

    // control.

    public void drawMeProfile(User user) {
        ViewParent parent = getAppBarParent();
        if (parent != null && parent instanceof NestedScrollAppBarLayout) {
            TransitionManager.beginDelayedTransition((ViewGroup) parent);
        }
        locationCon.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(user.location)) {
            locationTxt.setText(user.location);
        } else {
            locationTxt.setText("Unknown");
        }

        if (!TextUtils.isEmpty(user.bio)) {
            bioTxt.setText(user.bio);
        } else {
            bioTxt.setText("Download free, beautiful high-quality photos curated by " + user.first_name + " " + user.last_name);
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

    private boolean isSameTags(List<Tag> a, List<Tag> b) {
        if ((a == null || a.size() == 0) && (b == null || b.size() == 0)) {
            return true;
        } else if (a != null && a.size() != 0 && b != null && b.size() != 0 && a.size() == b.size()) {
            for (int i = 0; i < a.size(); i++) {
                if (!TextUtils.equals(a.get(i).getTitle(), b.get(i).getTitle())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
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
