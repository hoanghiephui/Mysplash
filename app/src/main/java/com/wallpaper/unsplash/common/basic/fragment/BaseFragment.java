package com.wallpaper.unsplash.common.basic.fragment;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;

import static com.wallpaper.unsplash.common.utils.DisplayUtils.getNavigationBarHeight;

/**
 * UnsplashApplication fragment.
 * <p>
 * Basic Fragment class for UnsplashApplication.
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    public AdView adView;
    public LinearLayout adContainer;
    public com.google.android.gms.ads.AdView adViewGoogle;

    // style.

    public abstract void initStatusBarStyle();

    public abstract void initNavigationBarStyle();

    /**
     * This method can tell you if we need set dark status bar style.
     *
     * @return If we need to set status bar style only white.
     */
    public abstract boolean needSetDarkStatusBar();

    // save instance.

    /**
     * Write large data to the BaseSavedStateFragment when application saving instance state.
     *
     * @param outState The BaseSavedStateFragment which is used to save large data.
     */
    public abstract void writeLargeData(BaseActivity.BaseSavedStateFragment outState);

    /**
     * Read large data from the BaseSavedStateFragment when application restarting.
     *
     * @param savedInstanceState The BaseSavedStateFragment which is used to save large data.
     */
    public abstract void readLargeData(BaseActivity.BaseSavedStateFragment savedInstanceState);

    // snack bar.

    /**
     * Get the container CoordinatorLayout of snack bar.
     *
     * @return The container layout of snack bar.
     */
    public abstract CoordinatorLayout getSnackbarContainer();

    // control.

    /**
     * Handle the result data from last activity.
     *
     * @param requestCode {@link android.app.Activity#onActivityResult(int, int, Intent)}.
     * @param resultCode  {@link android.app.Activity#onActivityResult(int, int, Intent)}.
     * @param data        {@link android.app.Activity#onActivityResult(int, int, Intent)}.
     */
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        // do nothing.
    }

    /**
     * This method can tell you if the list view need back to top when user press the back button.
     *
     * @return if list view need back to top.
     */
    public abstract boolean needBackToTop();

    public abstract void backToTop();

    public void showAds(String id, final String idGoogle, View view) {
        // Instantiate an AdView view
        adView = new AdView(getActivity(), id, AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        adContainer = view.findViewById(R.id.adViewContainer);

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

        if (getNavigationBarHeight(getActivity().getResources()) != 0) {

            /*adContainer.getLayoutParams().height =
                    LinearLayout;*/
            adContainer.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_padding));
            adContainer.requestLayout();
        } else {
            /*adContainer.getLayoutParams().height =
                    getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);*/
            adContainer.setPadding(0, 0, 0, 0);
            adContainer.requestLayout();
        }
    }

    public void showAdsGoogle(String id) {
        adViewGoogle = new com.google.android.gms.ads.AdView(getActivity());
        adViewGoogle.setAdUnitId(id);
        adViewGoogle.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
        final AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adViewGoogle.loadAd(adRequest);
        adViewGoogle.setAdListener(new com.google.android.gms.ads.AdListener(){
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

    @Override
    public void onDestroyView() {
        if (adView != null) {
            adView.destroy();
        }
        if (adViewGoogle != null) {
            adViewGoogle.destroy();
        }
        super.onDestroyView();
    }
}
