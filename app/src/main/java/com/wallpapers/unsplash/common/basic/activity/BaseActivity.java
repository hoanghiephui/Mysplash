package com.wallpapers.unsplash.common.basic.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.wallpapers.unsplash.ConnectionEvent;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.MysplashPopupWindow;
import com.wallpapers.unsplash.common.basic.fragment.BaseDialogFragment;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.LanguageUtils;
import com.wallpapers.unsplash.injection.Injector;
import com.wallpapers.unsplash.rx.RxBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static com.wallpapers.unsplash.common.utils.DisplayUtils.getNavigationBarHeight;

/**
 * UnsplashApplication activity.
 * <p>
 * The basic activity class for UnsplashApplication.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private Bundle bundle; // saved instance state.
    private boolean started; // flag of onStart() method.

    private List<BaseDialogFragment> dialogList = new ArrayList<>();
    private List<MysplashPopupWindow> popupList = new ArrayList<>();
    public AdView adView;
    public LinearLayout adContainer;
    public com.google.android.gms.ads.AdView adViewGoogle;
    private AlertDialog mAlertDialog;

    public CompositeDisposable compositeDisposable;
    public Observable<ConnectionEvent> connectionEventObservable;

    @Inject
    protected RxBus rxBus;

    {
        Injector.getInstance().getAppComponent().inject(this);
    }

    /**
     * Base saved state fragment.
     * <p>
     * This fragment is used to save large data when application is saving state instance.
     */
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
        if (savedInstanceState == null) {
            UnsplashApplication.getInstance().addActivity(this);
        } else {
            UnsplashApplication.getInstance().addActivityToFirstPosition(this);
        }
        compositeDisposable = new CompositeDisposable();

        connectionEventObservable = rxBus.register(ConnectionEvent.class);
        compositeDisposable.add(
                connectionEventObservable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(connectionEvent -> {
                            if (connectionEvent.equals(ConnectionEvent.PINNING_FAIL)) {
                                showAlertDialog(getString(R.string.ssl_pinning_invalid), true);
                            } else if (connectionEvent.equals(ConnectionEvent.NO_CONNECTION)) {
                                showAlertDialog(getString(R.string.ssl_no_connection), false);
                            }
                        }));
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
        if (adView != null) {
            adView.destroy();
        }
        if (adViewGoogle != null) {
            adViewGoogle.destroy();
        }
        UnsplashApplication.getInstance().removeActivity(this);
        rxBus.unregister(ConnectionEvent.class, connectionEventObservable);
        compositeDisposable.clear();
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        super.onDestroy();

    }

    // control style.

    protected abstract void setTheme();

    /**
     * If return true, child class will be responsible for the operation of the status bar.
     * Otherwise, BaseActivity class will deal with it.
     */
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
     */
    public abstract void handleBackPressed();

    /**
     * This method can make list view back to the top.
     */
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
     */
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
     */
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

    public void showAds(String id, final String idGoogle) {
        // Instantiate an AdView view
        adView = new AdView(this, id, AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        adContainer = findViewById(R.id.adViewContainer);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

        if (adView != null) {
            adView.setAdListener(new AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    //ad.loadAd();
                    Log.d(TAG, "onError: " + adError.getErrorMessage());
                    showAdsGoogle(idGoogle);

                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Log.d(TAG, "onAdLoaded: ");
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
        }
        if (getNavigationBarHeight(getResources()) != 0) {
            adContainer.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_padding));
            adContainer.requestLayout();
        } else {
            adContainer.setPadding(0, 0, 0, 0);
            adContainer.requestLayout();
        }
    }

    public void showAdsGoogle(String id) {
        adViewGoogle = new com.google.android.gms.ads.AdView(this);
        adViewGoogle.setAdUnitId(id);
        adViewGoogle.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
        final AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adViewGoogle.loadAd(adRequest);
        adViewGoogle.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                adViewGoogle.loadAd(adRequest);
                Log.d(TAG, "onAdFailedToLoad: " + errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.d(TAG, "onAdOpened: ");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
        if (adContainer != null) {
            adContainer.removeAllViews();
            adContainer.addView(adViewGoogle);
        }
    }

    private void showAlertDialog(final String message, final boolean forceExit) {
        if (mAlertDialog != null) mAlertDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setMessage(message)
                .setCancelable(false);

        builder.setNegativeButton(R.string.exit, (d, id) -> finish());

        mAlertDialog = builder.create();
        if (!isFinishing()) {
            mAlertDialog.show();
        }
    }
}
