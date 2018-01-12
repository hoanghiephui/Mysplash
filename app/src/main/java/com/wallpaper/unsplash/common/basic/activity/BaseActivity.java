package com.wallpaper.unsplash.common.basic.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.wallpaper.unsplash.BuildConfig;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.basic.fragment.BaseDialogFragment;
import com.wallpaper.unsplash.common.basic.MysplashPopupWindow;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.LanguageUtils;
import com.wallpaper.unsplash.common.utils.PrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * UnsplashApplication activity.
 *
 * The basic activity class for UnsplashApplication.
 *
 * */

public abstract class BaseActivity extends AppCompatActivity {

    private Bundle bundle; // saved instance state.
    private boolean started; // flag of onStart() method.

    private List<BaseDialogFragment> dialogList = new ArrayList<>();
    private List<MysplashPopupWindow> popupList = new ArrayList<>();
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    /**
     * Base saved state fragment.
     *
     * This fragment is used to save large data when application is saving state instance.
     *
     * */
    public abstract static class BaseSavedStateFragment extends Fragment {

        private static final String FRAGMENT_TAG = "SavedStateFragment";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // set this flag as true, otherwise the fragment will be rebuild when activity restart.
            setRetainInstance(true);
        }

        public void saveData(BaseActivity baseActivity) {
            Fragment f = baseActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            if (f != null) {
                baseActivity.getSupportFragmentManager().beginTransaction().remove(f).commit();
            }
            baseActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(this, FRAGMENT_TAG)
                    .commitAllowingStateLoss();
        }

        public static BaseSavedStateFragment getData(BaseActivity baseActivity) {
            Fragment f = baseActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            if (f != null) {
                baseActivity.getSupportFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
                return (BaseSavedStateFragment) f;
            } else {
                return null;
            }
        }
    }

    // life cycle.

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.local_config);
        fetchSetting();
        if (savedInstanceState == null) {
            UnsplashApplication.getInstance().addActivity(this);
        } else {
            UnsplashApplication.getInstance().addActivityToFirstPosition(this);
        }

        setTheme();
        LanguageUtils.setLanguage(this);
        DisplayUtils.setWindowTop(this);
        if (!operateStatusBarBySelf()) {
            DisplayUtils.setStatusBarStyle(this, false);
        }
        if (hasTranslucentNavigationBar()) {
            DisplayUtils.setNavigationBarStyle(this, false, hasTranslucentNavigationBar());
        }

        this.bundle = savedInstanceState;
        this.started = false;
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        UnsplashApplication.getInstance().removeActivity(this);
    }

    public void fetchSetting() {
        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        }
                        PrefsUtils.Companion.setAuthenBearer(BaseActivity.this, mFirebaseRemoteConfig.getString("authen_bearer"));
                        PrefsUtils.Companion.setAuthenId(BaseActivity.this, mFirebaseRemoteConfig.getString("authen_id"));
                        PrefsUtils.Companion.setShowAds(BaseActivity.this, mFirebaseRemoteConfig.getBoolean("isAds"));

                    }
                });
    }

    // control style.

    protected abstract void setTheme();

    /**
     * If return true, child class will be responsible for the operation of the status bar.
     * Otherwise, BaseActivity class will deal with it.
     * */
    protected boolean operateStatusBarBySelf() {
        return false;
    }

    public boolean hasTranslucentNavigationBar() {
        return false;
    }

    // handle back press action.

    @Override
    public void onBackPressed() {
        if (dialogList.size() > 0) {
            // has dialogs. --> dismiss the dialog which on the top of task.
            dialogList.get(dialogList.size() - 1).dismiss();
        } else if (popupList.size() > 0) {
            // has popup windows.
            popupList.get(popupList.size() - 1).dismiss();
        } else {
            // give the back pressed action to child class.
            handleBackPressed();
        }
    }

    /**
     * Consume the back pressed action.
     * */
    public abstract void handleBackPressed();

    /**
     * This method can make list view back to the top.
     * */
    protected abstract void backToTop();

    public abstract void finishActivity(int dir);

    @Override
    public void finish() {
        super.finish();
        UnsplashApplication.getInstance().removeActivity(this);
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        UnsplashApplication.getInstance().removeActivity(this);
    }

    // manage snack bar container.

    /**
     * Provide the container layout of snack bar. Include dialogs in this activity.
     *
     * @return The container of snack bar.
     * */
    public CoordinatorLayout provideSnackbarContainer() {
        if (dialogList.size() > 0) {
            // has dialogs. --> return the top dialog's snack bar container.
            return dialogList.get(dialogList.size() - 1).getSnackbarContainer();
        } else {
            // return the snack bar container of activity.
            return getSnackbarContainer();
        }
    }

    /**
     * Get the CoordinatorLayout as a container of snack bar in layout of activity or fragments.
     *
     * @return The container of snack bar.
     * */
    public abstract CoordinatorLayout getSnackbarContainer();

    // save instance state.

    public Bundle getBundle() {
        return bundle;
    }

    public void setStarted() {
        started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public List<BaseDialogFragment> getDialogList() {
        return dialogList;
    }

    public List<MysplashPopupWindow> getPopupList() {
        return popupList;
    }
}
