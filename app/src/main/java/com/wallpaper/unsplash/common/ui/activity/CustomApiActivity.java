package com.wallpaper.unsplash.common.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpaper.unsplash.common.ui.widget.coordinatorView.StatusBarView;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.helper.NotificationHelper;
import com.wallpaper.unsplash.common.utils.manager.CustomApiManager;
import com.wallpaper.unsplash.common.utils.manager.ThemeManager;
import com.wallpaper.unsplash.common.utils.widget.SafeHandler;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Custom api dialog.
 *
 * This activity can help user to save personal API key.
 *
 * */

public class CustomApiActivity extends BaseActivity
        implements SwipeBackCoordinatorLayout.OnSwipeListener, SafeHandler.HandlerContainer {

    @BindView(R.id.activity_custom_api_container)
    CoordinatorLayout container;

    @BindView(R.id.activity_custom_api_statusBar)
    StatusBarView statusBar;

    @BindView(R.id.activity_custom_api_key)
    EditText key;

    @BindView(R.id.activity_custom_api_secret)
    EditText secret;

    private SafeHandler<CustomApiActivity> handler;

    private boolean backPressed = false; // mark the first click action.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_api);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isStarted()) {
            setStarted();
            ButterKnife.bind(this);
            initWidget();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setTheme() {
        if (ThemeManager.getInstance(this).isLightTheme()) {
            setTheme(R.style.MysplashTheme_light_Translucent_Common);
        } else {
            setTheme(R.style.MysplashTheme_dark_Translucent_Common);
        }
    }

    @Override
    public void handleBackPressed() {
        // double click to exit.
        if (backPressed) {
            finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
        } else {
            backPressed = true;
            NotificationHelper.showSnackbar(getString(R.string.feedback_click_again_to_exit));

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.obtainMessage(1).sendToTarget();
                }
            }, 2000);
        }
    }

    @Override
    protected void backToTop() {
        // do nothing.
    }

    @Override
    public void finishActivity(int dir) {
        finish();
        switch (dir) {
            case SwipeBackCoordinatorLayout.UP_DIR:
                overridePendingTransition(0, R.anim.activity_slide_out_top);
                break;

            case SwipeBackCoordinatorLayout.DOWN_DIR:
                overridePendingTransition(0, R.anim.activity_slide_out_bottom);
                break;
        }
    }

    @Override
    public CoordinatorLayout getSnackbarContainer() {
        return container;
    }

    private void initWidget() {
        this.handler = new SafeHandler<>(this);

        SwipeBackCoordinatorLayout swipeBackView = ButterKnife.findById(
                this, R.id.activity_custom_api_swipeBackView);
        swipeBackView.setOnSwipeListener(this);

        ImageButton closeBtn = ButterKnife.findById(this, R.id.activity_custom_api_closeBtn);
        ThemeManager.setImageResource(closeBtn, R.drawable.ic_close_light, R.drawable.ic_close_dark);

        DisplayUtils.setTypeface(this, key);
        if (!TextUtils.isEmpty(CustomApiManager.getInstance(this).getCustomApiKey())) {
            key.setText(CustomApiManager.getInstance(this).getCustomApiKey());
        }

        DisplayUtils.setTypeface(this, secret);
        if (!TextUtils.isEmpty(CustomApiManager.getInstance(this).getCustomApiSecret())) {
            secret.setText(CustomApiManager.getInstance(this).getCustomApiSecret());
        }

        TextView redirectUri = (TextView) findViewById(R.id.activity_custom_api_redirectUri);
        DisplayUtils.setTypeface(this, redirectUri);
    }

    // interface.

    // on click listener.

    @OnClick({
            R.id.activity_custom_api_closeBtn,
            R.id.activity_custom_api_cancelBtn}) void cancel() {
        finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
    }

    @OnClick(R.id.activity_custom_api_enterBtn) void enter() {
        boolean changed = CustomApiManager.getInstance(this)
                .setCustomApi(
                        this,
                        key.getText().toString(),
                        secret.getText().toString());

        Intent intent = new Intent();
        setResult(changed ? RESULT_OK : RESULT_CANCELED, intent);
        finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
    }

    // on swipe listener.

    @Override
    public boolean canSwipeBack(int dir) {
        return true;
    }

    @Override
    public void onSwipeProcess(float percent) {
        statusBar.setAlpha(1 - percent);
        container.setBackgroundColor(SwipeBackCoordinatorLayout.getBackgroundColor(percent));
    }

    @Override
    public void onSwipeFinish(int dir) {
        finishActivity(dir);
    }

    // handler.

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                backPressed = false;
                break;
        }
    }
}
