package com.wallpapers.unsplash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wallpapers.unsplash.annotations.Thunk;
import com.wallpapers.unsplash.api.EnvironmentSettings;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.basic.activity.LoadableActivity;
import com.wallpapers.unsplash.common.basic.activity.RequestLoadActivity;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.utils.PrefsUtils;
import com.wallpapers.unsplash.common.utils.manager.CustomApiManager;
import com.wallpapers.unsplash.injection.Injector;
import com.wallpapers.unsplash.main.view.activity.MainActivity;
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity;
import com.wallpapers.unsplash.rx.RxBus;

import io.fabric.sdk.android.Fabric;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;
import retrofit2.Retrofit;

/**
 * UnsplashApplication.
 * <p>
 * Application class for UnsplashApplication.
 */

public class UnsplashApplication extends MultiDexApplication implements FrameworkInterface {
    private static final String TAG = UnsplashApplication.class.getSimpleName();

    private static UnsplashApplication instance;

    public static UnsplashApplication getInstance() {
        return instance;
    }

    private List<BaseActivity> activityList;

    private Photo photo;

    public static final String UNSPLASH_API_BASE_URL = "https://api.unsplash.com/";
    public static final String STREAM_API_BASE_URL = "https://api.getstream.io/";
    public static final String UNSPLASH_TREND_FEEDING_URL = "napi/feeds/home";
    public static final String UNSPLASH_FOLLOWING_FEED_URL = "napi/feeds/following";
    public static final String UNSPLASH_NODE_API_URL = "napi/feeds/enrich";
    public static final String UNSPLASH_URL = "https://unsplash.com/";
    public static final String UNSPLASH_JOIN_URL = "https://unsplash.com/join";
    public static final String UNSPLASH_SUBMIT_URL = "https://unsplash.com/submit";
    public static final String UNSPLASH_LOGIN_CALLBACK = "unsplash-auth-callback";

    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String DOWNLOAD_PATH = "/Pictures/UnsplashApplication/";
    public static final String DOWNLOAD_PHOTO_FORMAT = ".jpg";
    public static final String DOWNLOAD_COLLECTION_FORMAT = ".zip";

    @StringDef({DOWNLOAD_PHOTO_FORMAT, DOWNLOAD_COLLECTION_FORMAT})
    public @interface DownloadFormatRule {
    }

    public static final int DEFAULT_PER_PAGE = 10;

    @IntRange(from = 1, to = 30)
    public @interface PerPageRule {
    }

    @IntRange(from = 1)
    public @interface PageRule {
    }

    public static final int CATEGORY_TOTAL_NEW = 0;
    public static final int CATEGORY_TOTAL_FEATURED = 1;
    public static final int CATEGORY_BUILDINGS_ID = 2;
    public static final int CATEGORY_FOOD_DRINK_ID = 3;
    public static final int CATEGORY_NATURE_ID = 4;
    public static final int CATEGORY_OBJECTS_ID = 8;
    public static final int CATEGORY_PEOPLE_ID = 6;
    public static final int CATEGORY_TECHNOLOGY_ID = 7;
    public static final int CATEGORY_PHOTO_TAG = 5;
    public static final int CATEGORY_USERS_QUERY_TAG = 9;

    @IntDef({CATEGORY_TOTAL_NEW, CATEGORY_TOTAL_FEATURED, CATEGORY_PHOTO_TAG})
    public @interface PhotosTypeRule {
    }

    @IntDef({
            CATEGORY_BUILDINGS_ID,
            CATEGORY_FOOD_DRINK_ID,
            CATEGORY_NATURE_ID,
            CATEGORY_OBJECTS_ID,
            CATEGORY_PEOPLE_ID,
            CATEGORY_TECHNOLOGY_ID})
    public @interface CategoryIdRule {
    }

    public static final int COLLECTION_TYPE_FEATURED = 0;
    public static final int COLLECTION_TYPE_ALL = 1;
    public static final int COLLECTION_TYPE_CURATED = 2;
    public static final int COLLECTION_TYPE_QUERY = 3;

    @IntDef({COLLECTION_TYPE_FEATURED, COLLECTION_TYPE_ALL, COLLECTION_TYPE_CURATED, COLLECTION_TYPE_QUERY})
    public @interface CollectionTypeRule {
    }

    public static int TOTAL_NEW_PHOTOS_COUNT = 17444;
    public static int TOTAL_FEATURED_PHOTOS_COUNT = 1192;
    public static int BUILDING_PHOTOS_COUNT = 2720;
    public static int FOOD_DRINK_PHOTOS_COUNT = 650;
    public static int NATURE_PHOTOS_COUNT = 54208;
    public static int OBJECTS_PHOTOS_COUNT = 2150;
    public static int PEOPLE_PHOTOS_COUNT = 3410;
    public static int TECHNOLOGY_PHOTOS_COUNT = 350;

    public static final int COLLECTION_ACTIVITY = 1;
    public static final int USER_ACTIVITY = 2;
    public static final int ME_ACTIVITY = 3;
    public static final int CUSTOM_API_ACTIVITY = 4;

    public static final int SEARCH_QUERY_ID = 11;
    public static final int EXPRORE_ID = 12;

    @Inject
    @Named("api")
    protected Lazy<Retrofit> retrofitApi;

    @Inject
    @Named("api_root")
    protected Lazy<Retrofit> retrofitApiRoot;
    @Inject
    RxBus rxBus;
    @Inject
    EnvironmentSettings environmentSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        // Init objects first
        Injector.getInstance().init(this);
        // Inject into Application
        Injector.getInstance().getAppComponent().inject(this);
        UnsplashFramework.init(this);
        checkSecurityProviderAndPatchIfNeeded();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        instance = this;
        MobileAds.initialize(getApplicationContext(), Constants.ADMOB_APP_ID);
        activityList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("config")
                .document("authen_id")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String authenBearer = documentSnapshot.getString("authen_bearer");
                    String authenID = documentSnapshot.getString("authen_id");
                    boolean isAds = documentSnapshot.getBoolean("isAds");
                    PrefsUtils.Companion.setAuthenBearer(getApplicationContext(), authenBearer);
                    PrefsUtils.Companion.setAuthenId(getApplicationContext(), authenID);
                    PrefsUtils.Companion.setShowAds(getApplicationContext(), isAds);
                    rxBus.emitEvent(ConnectionEvent.class, ConnectionEvent.SUCCESS_FIREBASE);
                    Log.d(TAG, "onSuccess: " + authenBearer + " ");
                })
                /*.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                })*/
                .addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.getMessage()));
    }

    public static String getAppId(Context context, boolean auth) {
        if (isDebug(context)) {
            return BuildConfig.APP_ID_BETA;
        } else if (TextUtils.isEmpty(CustomApiManager.getInstance(context).getCustomApiKey())
                || TextUtils.isEmpty(CustomApiManager.getInstance(context).getCustomApiSecret())) {
            if (auth) {
                return BuildConfig.APP_ID_RELEASE;
            } else {
                return BuildConfig.APP_ID_RELEASE_UNAUTH;
            }
        } else {
            return CustomApiManager.getInstance(context).getCustomApiKey();
        }
    }

    public static String getSecret(Context c) {
        if (isDebug(c)) {
            return BuildConfig.SECRET_BETA;
        } else if (TextUtils.isEmpty(CustomApiManager.getInstance(c).getCustomApiKey())
                || TextUtils.isEmpty(CustomApiManager.getInstance(c).getCustomApiSecret())) {
            return BuildConfig.SECRET_RELEASE;
        } else {
            return CustomApiManager.getInstance(c).getCustomApiSecret();
        }
    }

    public static boolean isDebug(Context c) {
        try {
            return (c.getApplicationInfo().flags
                    & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception ignored) {

        }
        return false;
    }

    public static String getLoginUrl(Context c) {
        return UnsplashApplication.UNSPLASH_URL + "oauth/authorize"
                + "?client_id=" + getAppId(c, true)
                + "&redirect_uri=" + "livingphoto%3A%2F%2F" + UNSPLASH_LOGIN_CALLBACK
                + "&response_type=" + "code"
                + "&scope=" + "public+read_user+write_user+read_photos+write_photos+write_likes+write_followers+read_collections+write_collections";
    }

    public void addActivity(@NonNull BaseActivity a) {
        for (BaseActivity activity : activityList) {
            if (activity.equals(a)) {
                return;
            }
        }
        activityList.add(a);
    }

    public void addActivityToFirstPosition(@NonNull BaseActivity a) {
        for (BaseActivity activity : activityList) {
            if (activity.equals(a)) {
                return;
            }
        }
        activityList.add(0, a);
    }

    public void removeActivity(BaseActivity a) {
        activityList.remove(a);
    }

    @Nullable
    public BaseActivity getTopActivity() {
        if (activityList != null && activityList.size() > 0) {
            return activityList.get(activityList.size() - 1);
        } else {
            return null;
        }
    }

    @Nullable
    public MainActivity getMainActivity() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) instanceof MainActivity) {
                    return (MainActivity) activityList.get(i);
                }
            }
        }
        return null;
    }

    public int getActivityCount() {
        if (activityList != null) {
            return activityList.size();
        } else {
            return 0;
        }
    }

    @Nullable
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<Photo> loadMorePhotos(PhotoActivity activity,
                                      List<Photo> list, int headIndex, boolean headDirection,
                                      Bundle bundle) {
        int index = activityList.indexOf(activity) - 1;
        if (index > -1) {
            Activity a = activityList.get(index);
            if (a instanceof LoadableActivity) {
                if (((ParameterizedType) a.getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0]
                        .toString()
                        .equals(Photo.class.toString())) {
                    try {
                        return ((LoadableActivity) a).loadMoreData(list, headIndex, headDirection, bundle);
                    } catch (Exception ignore) {

                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public void dispatchPhotoUpdate(PhotoActivity activity, Photo p) {
        int index = activityList.indexOf(activity) - 1;
        if (index > -1) {
            Activity a = activityList.get(index);
            if (a instanceof LoadableActivity) {
                if (((ParameterizedType) a.getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0]
                        .toString()
                        .equals(Photo.class.toString())) {
                    try {
                        ((LoadableActivity) a).updateData(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void dispatchPhotoUpdate(LoadableActivity<Photo> activity, Photo p) {
        int index = activityList.indexOf(activity) + 1;
        if (index < activityList.size()) {
            Activity a = activityList.get(index);
            if (a instanceof RequestLoadActivity) {
                if (((ParameterizedType) a.getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0]
                        .toString()
                        .equals(Photo.class.toString())) {
                    try {
                        ((RequestLoadActivity) a).updateData(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * This patches a device's Security Provider asynchronously to help defend against various
     * vulnerabilities. This provider is normally updated in Google Play Services anyway, but this
     * will catch any immediate issues that haven't been fixed in a slow rollout.
     * <p>
     * In the future, we may want to show some kind of warning to users or even stop the app, but
     * this will harm users with versions of Android without GMS approval.
     *
     * @see <a href="https://developer.android.com/training/articles/security-gms-provider.html">Updating
     * Your Security Provider</a>
     */
    protected void checkSecurityProviderAndPatchIfNeeded() {
        ProviderInstaller.installIfNeededAsync(this, new ProviderInstaller.ProviderInstallListener() {
            @Override
            public void onProviderInstalled() {
                Log.i(TAG, "Security Provider installed");
            }

            @Override
            public void onProviderInstallFailed(int errorCode, Intent intent) {
                if (GoogleApiAvailability.getInstance().isUserResolvableError(errorCode)) {
                    showError(errorCode);
                } else {
                    // Google Play services is not available.
                    onProviderInstallerNotAvailable();
                }
            }
        });
    }

    /**
     * Show a dialog prompting the user to install/update/enable Google Play services.
     *
     * @param errorCode Recoverable error code
     */
    @Thunk
    void showError(int errorCode) {
        // TODO: 05/08/2016 Decide if we should alert users here or not

    }

    /**
     * This is reached if the provider cannot be updated for some reason. App should consider all
     * HTTP communication to be vulnerable, and take appropriate action.
     */
    @Thunk
    void onProviderInstallerNotAvailable() {
        // TODO: 05/08/2016 Decide if we should take action here or not

    }

    @Override
    public Retrofit getRetrofitApiInstance() {
        return retrofitApi.get();
    }

    @Override
    public Retrofit getRetrofitApiRootInstance() {
        return retrofitApiRoot.get();
    }
}
