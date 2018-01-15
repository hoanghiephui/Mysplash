package com.wallpaper.unsplash.main.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.LoadableActivity;
import com.wallpaper.unsplash.common.basic.fragment.BaseFragment;
import com.wallpaper.unsplash.common.basic.fragment.LoadableFragment;
import com.wallpaper.unsplash.common.data.entity.unsplash.Collection;
import com.wallpaper.unsplash.common.data.entity.unsplash.FollowingResult;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.interfaces.model.DownloadModel;
import com.wallpaper.unsplash.common.interfaces.presenter.DownloadPresenter;
import com.wallpaper.unsplash.common.ui.activity.invisible.RestartActivity;
import com.wallpaper.unsplash.common.ui.adapter.PhotoAdapter;
import com.wallpaper.unsplash.common.ui.widget.CircleImageView;
import com.wallpaper.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.common.utils.helper.ImageHelper;
import com.wallpaper.unsplash.common.utils.manager.AuthManager;
import com.wallpaper.unsplash.common.interfaces.model.DrawerModel;
import com.wallpaper.unsplash.common.interfaces.presenter.DrawerPresenter;
import com.wallpaper.unsplash.common.interfaces.presenter.FragmentManagePresenter;
import com.wallpaper.unsplash.common.interfaces.presenter.MeManagePresenter;
import com.wallpaper.unsplash.common.interfaces.presenter.MessageManagePresenter;
import com.wallpaper.unsplash.common.interfaces.view.DrawerView;
import com.wallpaper.unsplash.common.interfaces.view.MeManageView;
import com.wallpaper.unsplash.common.utils.BackToTopUtils;
import com.wallpaper.unsplash.common.utils.manager.ShortcutsManager;
import com.wallpaper.unsplash.common.utils.manager.ThemeManager;
import com.wallpaper.unsplash.common.utils.manager.ThreadManager;
import com.wallpaper.unsplash.main.model.activity.DownloadObject;
import com.wallpaper.unsplash.main.model.activity.DrawerObject;
import com.wallpaper.unsplash.main.model.activity.FragmentManageObject;
import com.wallpaper.unsplash.common.interfaces.model.FragmentManageModel;
import com.wallpaper.unsplash.common.interfaces.view.MessageManageView;
import com.wallpaper.unsplash.main.presenter.activity.DownloadImplementor;
import com.wallpaper.unsplash.main.presenter.activity.DrawerImplementor;
import com.wallpaper.unsplash.main.presenter.activity.FragmentManageImplementor;
import com.wallpaper.unsplash.main.presenter.activity.MeManageImplementor;
import com.wallpaper.unsplash.main.presenter.activity.MessageManageImplementor;
import com.wallpaper.unsplash.common.utils.widget.SafeHandler;
import com.wallpaper.unsplash.main.view.fragment.CategoryFragment;
import com.wallpaper.unsplash.main.view.fragment.FollowingFragment;
import com.wallpaper.unsplash.main.view.fragment.HomeFragment;
import com.wallpaper.unsplash.main.view.fragment.MultiFilterFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity.
 * */

public class MainActivity extends LoadableActivity<Photo>
        implements MessageManageView, MeManageView, DrawerView,
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,
        PhotoAdapter.OnDownloadPhotoListener, AuthManager.OnAuthDataChangedListener,
        SafeHandler.HandlerContainer {

    @BindView(R.id.activity_main_drawerLayout)
    DrawerLayout drawer;

    @BindView(R.id.activity_main_navView)
    NavigationView nav;

    private ImageView appIcon;
    private CircleImageView navAvatar;
    private TextView navTitle;
    private TextView navSubtitle;
    private ImageButton navButton;

    private SafeHandler<MainActivity> handler;

    private FragmentManageModel fragmentManageModel;
    private FragmentManagePresenter fragmentManagePresenter;

    private MessageManagePresenter messageManagePresenter;

    private MeManagePresenter meManagePresenter;

    private DrawerModel drawerModel;
    private DrawerPresenter drawerPresenter;

    private DownloadModel downloadModel;
    private DownloadPresenter downloadPresenter;

    public static final String ACTION_SEARCH = "com.wallpaper.unsplash.Search";

    private final String KEY_MAIN_ACTIVITY_FRAGMENT_ID = "main_activity_fragment_id";
    private final String KEY_MAIN_ACTIVITY_SELECTED_ID = "main_activity_selected_id";

    private final int REFRESH_PROFILE = 1;
    private final int REFRESH_SHORTCUTS = 2;
    private final int DRAW_PROFILE = 3;

    private Runnable initRunnable = new Runnable() {
        @Override
        public void run() {
            // add auth listener.
            AuthManager.getInstance().addOnWriteDataListener(MainActivity.this);

            // refresh profile or shortcuts.
            if (AuthManager.getInstance().isAuthorized()
                    && (TextUtils.isEmpty(AuthManager.getInstance().getUsername())
                    || AuthManager.getInstance().getNumericId() < 0)) {
                handler.obtainMessage(REFRESH_PROFILE).sendToTarget();
            } else {
                handler.obtainMessage(REFRESH_SHORTCUTS).sendToTarget();
            }

            // check to show introduce.
            //IntroduceActivity.checkAndStartIntroduce(MainActivity.this);

            // draw profile.
            handler.obtainMessage(DRAW_PROFILE).sendToTarget();
        }
    };

    public static class SavedStateFragment extends BaseSavedStateFragment {
        // data
        private List<Photo> homeTrendingList;
        private List<Photo> homeNewList;
        private List<Photo> homeFeaturedList;

        private List<FollowingResult> followingFeedList;

        private List<Collection> featuredCollectionList;
        private List<Collection> allCollectionList;
        private List<Collection> curatedCollectionList;

        private List<Photo> multiFilterList;

        private List<Photo> categoryList;
        private List<Photo> photoQueryList;
        private List<Collection> queryCollectionList;
        private List<User> queryUsersList;

        // data.

        public List<Photo> getHomeTrendingList() {
            return homeTrendingList;
        }

        public void setHomeTrendingList(List<Photo> homeTrendingList) {
            this.homeTrendingList = homeTrendingList;
        }

        public List<Photo> getHomeNewList() {
            return homeNewList;
        }

        public void setHomeNewList(List<Photo> homeNewList) {
            this.homeNewList = homeNewList;
        }

        public List<Photo> getHomeFeaturedList() {
            return homeFeaturedList;
        }

        public void setHomeFeaturedList(List<Photo> homeFeaturedList) {
            this.homeFeaturedList = homeFeaturedList;
        }

        public List<Collection> getFeaturedCollectionList() {
            return featuredCollectionList;
        }

        public void setFeaturedCollectionList(List<Collection> featuredCollectionList) {
            this.featuredCollectionList = featuredCollectionList;
        }

        public List<Collection> getAllCollectionList() {
            return allCollectionList;
        }

        public void setAllCollectionList(List<Collection> allCollectionList) {
            this.allCollectionList = allCollectionList;
        }

        public List<Collection> getCuratedCollectionList() {
            return curatedCollectionList;
        }

        public void setCuratedCollectionList(List<Collection> curatedCollectionList) {
            this.curatedCollectionList = curatedCollectionList;
        }

        public List<FollowingResult> getFollowingFeedList() {
            return followingFeedList;
        }

        public void setFollowingFeedList(List<FollowingResult> followingFeedList) {
            this.followingFeedList = followingFeedList;
        }

        public List<Photo> getMultiFilterList() {
            return multiFilterList;
        }

        public void setMultiFilterList(List<Photo> multiFilterList) {
            this.multiFilterList = multiFilterList;
        }

        public List<Photo> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<Photo> categoryList) {
            this.categoryList = categoryList;
        }

        public List<Photo> getPhotoQueryList() {
            return photoQueryList;
        }

        public void setPhotoQueryList(List<Photo> photoQueryList) {
            this.photoQueryList = photoQueryList;
        }

        public List<Collection> getQueryCollectionList() {
            return queryCollectionList;
        }

        public void setQueryCollectionList(List<Collection> queryCollectionList) {
            this.queryCollectionList = queryCollectionList;
        }

        public List<User> getQueryUsersList() {
            return queryUsersList;
        }

        public void setQueryUsersList(List<User> queryUsersList) {
            this.queryUsersList = queryUsersList;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initModel(savedInstanceState);
        initPresenter();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (isSearchIntent(intent)) {
            changeFragment(R.id.action_search);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isStarted()) {
            setStarted();
            ButterKnife.bind(this);
            initView();
            buildFragmentStack();
            ThreadManager.getInstance().execute(initRunnable);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseFragment fragment = getTopFragment();
        if (fragment != null && data != null) {
            fragment.handleActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == UnsplashApplication.ME_ACTIVITY) {
            drawMeAvatar();
        }
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        AuthManager.getInstance().refreshPersonalNotifications();
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AuthManager.getInstance().removeOnWriteDataListener(this);
        AuthManager.getInstance().cancelRequest();
    }

    @Override
    protected void setTheme() {
        if (ThemeManager.getInstance(this).isLightTheme()) {
            setTheme(R.style.MysplashTheme_light_Main);
        } else {
            setTheme(R.style.MysplashTheme_dark_Main);
        }
        if (DisplayUtils.isLandscape(this)) {
            DisplayUtils.cancelTranslucentNavigation(this);
        }
    }

    @Override
    public boolean hasTranslucentNavigationBar() {
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save large data.
        SavedStateFragment f = new SavedStateFragment();
        List<BaseFragment> fragmentList = fragmentManagePresenter.getFragmentList(this, true);
        for (int i = 0; i < fragmentList.size(); i ++) {
            fragmentList.get(i).writeLargeData(f);
        }
        f.saveData(this);

        // save normal data.
        super.onSaveInstanceState(outState);
        outState.putInt(
                KEY_MAIN_ACTIVITY_FRAGMENT_ID,
                fragmentManagePresenter.getId());
        outState.putInt(
                KEY_MAIN_ACTIVITY_SELECTED_ID,
                drawerPresenter.getCheckedItemId());
    }

    @Override
    public void handleBackPressed() {
        DrawerLayout drawer = findViewById(R.id.activity_main_drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            BaseFragment f = getTopFragment();
            if (f != null
                    && f.needBackToTop() && BackToTopUtils.isSetBackToTop(true)) {
                f.backToTop();
            } else if (f instanceof HomeFragment) {
                finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
            } else {
                changeFragment(R.id.action_home);
            }
        }
    }

    @Override
    protected void backToTop() {
        // do nothing.
    }

    @Override
    public void finishActivity(int dir) {
        finish();
    }

    @Override
    public CoordinatorLayout getSnackbarContainer() {
        BaseFragment fragment = getTopFragment();
        if (fragment == null) {
            return null;
        } else {
            return fragment.getSnackbarContainer();
        }
    }

    @Override
    public List<Photo> loadMoreData(List<Photo> list, int headIndex, boolean headDirection, Bundle bundle) {
        BaseFragment fragment = getTopFragment();
        if (fragment != null) {
            int id = bundle.getInt(KEY_MAIN_ACTIVITY_FRAGMENT_ID, 0);
            switch (id) {
                case R.id.action_home:
                    if (fragment instanceof HomeFragment) {
                        return ((HomeFragment) fragment).loadMoreData(list, headIndex, headDirection, bundle);
                    }
                    break;

                case R.id.action_following:
                    if (fragment instanceof FollowingFragment) {
                        return ((FollowingFragment) fragment).loadMoreData(list, headIndex, headDirection, bundle);
                    }
                    break;

                case R.id.action_multi_filter:
                    if (fragment instanceof MultiFilterFragment) {
                        return ((MultiFilterFragment) fragment).loadMoreData(list, headIndex, headDirection, bundle);
                    }
                    break;

                case R.id.action_category:
                    if (fragment instanceof CategoryFragment) {
                        return ((CategoryFragment) fragment).loadMoreData(list, headIndex, headDirection, bundle);
                    }
                    break;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Bundle getBundleOfList() {
        Bundle bundle = new Bundle();
        BaseFragment fragment = getTopFragment();
        if (fragment != null && fragment instanceof LoadableFragment) {
            bundle.putInt(KEY_MAIN_ACTIVITY_FRAGMENT_ID, fragmentManagePresenter.getId());
            return ((LoadableFragment) fragment).getBundleOfList(bundle);
        } else {
            return bundle;
        }
    }

    @Override
    public void updateData(Photo photo) {
        BaseFragment fragment = getTopFragment();
        if (fragment != null && fragment instanceof LoadableFragment) {
            ((LoadableFragment) fragment).updateData(photo);
        }
    }

    // init.

    private void initModel(@Nullable Bundle savedInstanceState) {
        int fragmentId = R.id.action_home;
        int selectedId = R.id.action_home;
        if (savedInstanceState != null) {
            fragmentId = savedInstanceState.getInt(KEY_MAIN_ACTIVITY_FRAGMENT_ID, fragmentId);
            selectedId = savedInstanceState.getInt(KEY_MAIN_ACTIVITY_SELECTED_ID, selectedId);
        } else if (isSearchIntent(getIntent())) {
            fragmentId = R.id.action_search;
            selectedId = R.id.action_search;
        }

        this.fragmentManageModel = new FragmentManageObject(fragmentId, getIntent());
        this.drawerModel = new DrawerObject(selectedId);
        this.downloadModel = new DownloadObject();
    }

    private void initPresenter() {
        this.fragmentManagePresenter = new FragmentManageImplementor(fragmentManageModel);
        this.messageManagePresenter = new MessageManageImplementor(this);
        this.meManagePresenter = new MeManageImplementor(this);
        this.drawerPresenter = new DrawerImplementor(drawerModel, this);
        this.downloadPresenter = new DownloadImplementor(downloadModel);
    }

    private void initView() {
        this.handler = new SafeHandler<>(this);

        if (ThemeManager.getInstance(this).isLightTheme()) {
            nav.inflateMenu(R.menu.activity_main_drawer_light);
        } else {
            nav.inflateMenu(R.menu.activity_main_drawer_dark);
        }
        nav.setCheckedItem(drawerPresenter.getCheckedItemId());
        nav.setNavigationItemSelectedListener(this);

        if (AuthManager.getInstance().isAuthorized()) {
            nav.getMenu().getItem(1).setVisible(true);
        } else {
            nav.getMenu().getItem(1).setVisible(false);
        }

        View header = nav.getHeaderView(0);
        header.setOnClickListener(this);

        this.navAvatar = ButterKnife.findById(header, R.id.container_nav_header_avatar);

        this.appIcon = ButterKnife.findById(header, R.id.container_nav_header_appIcon);
        ImageHelper.loadResourceImage(this, appIcon, R.drawable.ic_launcher);

        this.navTitle = ButterKnife.findById(header, R.id.container_nav_header_title);
        DisplayUtils.setTypeface(this, navTitle);

        this.navSubtitle = ButterKnife.findById(header, R.id.container_nav_header_subtitle);
        DisplayUtils.setTypeface(this, navSubtitle);

        this.navButton = ButterKnife.findById(header, R.id.container_nav_header_button);
        navButton.setOnClickListener(this);
    }

    private void buildFragmentStack() {
        BaseSavedStateFragment f = SavedStateFragment.getData(this);
        if (f != null && f instanceof SavedStateFragment) {
            List<BaseFragment> fragmentList = fragmentManagePresenter.getFragmentList(this, true);
            for (int i = 0; i < fragmentList.size(); i ++) {
                fragmentList.get(i).readLargeData(f);
            }
        } else {
            changeFragment(fragmentManagePresenter.getId());
        }
    }

    // control.

    private void changeTheme() {
        DisplayUtils.changeTheme(this);
        reboot();
    }

    public void reboot() {
        Intent intent = new Intent(this, RestartActivity.class);
        startActivity(intent);
        overridePendingTransition(0, android.R.anim.fade_out);
        finish();
    }

    public void changeFragment(int code) {
        drawerPresenter.setCheckedItemId(code);
        fragmentManagePresenter.changeFragment(this, code, null);
    }

    public void changeFragment(int code, String title) {
        drawerPresenter.setCheckedItemId(code);
        fragmentManagePresenter.changeFragment(this, code, title);
    }

    @Nullable
    public BaseFragment getTopFragment() {
        return fragmentManagePresenter.getTopFragment(this);
    }

    private boolean isSearchIntent(Intent intent) {
        return !(intent == null || TextUtils.isEmpty(intent.getAction()))
                && intent.getAction().equals(ACTION_SEARCH);
    }

    // permission.

    @Override
    protected void requestReadWritePermissionSucceed(int requestCode) {
        downloadPresenter.download(this);
    }

    // interface.

    // on click listener.

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.container_nav_header:
                meManagePresenter.touchMeAvatar(this);
                break;

            case R.id.container_nav_header_button:
                meManagePresenter.touchMeButton(this);
                break;
        }
    }

    // on navigation item select listener.

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerPresenter.touchNavItem(item.getItemId());
        return true;
    }

    // on download photo listener. (photo adapter)

    @Override
    public void onDownload(Photo photo) {
        downloadPresenter.setDownloadKey(photo);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            downloadPresenter.download(this);
        } else {
            requestReadWritePermission();
        }
    }

    // on write data listener. (authorize manager)

    @SuppressLint("SetTextI18n")
    @Override
    public void onWriteAccessToken() {
        nav.getMenu().getItem(1).setVisible(true);
        meManagePresenter.responseWriteAccessToken();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onWriteUserInfo() {
        meManagePresenter.responseWriteUserInfo();
    }

    @Override
    public void onWriteAvatarPath() {
        meManagePresenter.responseWriteAvatarPath();
    }

    @Override
    public void onLogout() {
        nav.getMenu().getItem(1).setVisible(false);
        meManagePresenter.responseLogout();
    }

    // handler.

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case REFRESH_PROFILE:
                AuthManager.getInstance().requestPersonalProfile();
                break;

            case REFRESH_SHORTCUTS:
                if (Build.VERSION.SDK_INT >= 25) {
                    ShortcutsManager.refreshShortcuts(MainActivity.this);
                }
                break;

            case DRAW_PROFILE:
                drawMeAvatar();
                drawMeTitle();
                drawMeSubtitle();
                drawMeButton();
                break;

            default:
                messageManagePresenter.responseMessage(message.what, message.obj);
                break;
        }
    }

    // view.

    // message manage view.

    @Override
    public void sendMessage(final int what, final Object o) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage(what, o).sendToTarget();
            }
        }, 400);
    }

    @Override
    public void responseMessage(int what, Object o) {
        switch (what) {
            case R.id.action_change_theme:
                changeTheme();
                break;

            case R.id.action_download_manage:
                IntentHelper.startDownloadManageActivity(this);
                break;

            case R.id.action_settings:
                IntentHelper.startSettingsActivity(this);
                break;

            case R.id.action_about:
                IntentHelper.startAboutActivity(this);
                break;

            default:
                changeFragment(what);
                break;
        }
    }

    // me manage view.

    @Override
    public void drawMeAvatar() {
        if (!AuthManager.getInstance().isAuthorized()) {
            appIcon.setVisibility(View.VISIBLE);
            navAvatar.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(AuthManager.getInstance().getAvatarPath())) {
            navAvatar.setVisibility(View.VISIBLE);
            appIcon.setVisibility(View.GONE);
            ImageHelper.loadAvatar(this, navAvatar, new User());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                navAvatar.setTransitionName(AuthManager.getInstance().getAccessToken());
            }
        } else {
            navAvatar.setVisibility(View.VISIBLE);
            appIcon.setVisibility(View.GONE);
            ImageHelper.loadAvatar(
                    this, navAvatar, AuthManager.getInstance().getAvatarPath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                navAvatar.setTransitionName(AuthManager.getInstance().getAccessToken());
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void drawMeTitle() {
        if (!AuthManager.getInstance().isAuthorized()) {
            navTitle.setText("LOGIN");
        } else if (TextUtils.isEmpty(AuthManager.getInstance().getFirstName())
                || TextUtils.isEmpty(AuthManager.getInstance().getLastName())) {
            navTitle.setText("");
        } else {
            navTitle.setText(AuthManager.getInstance().getFirstName()
                    + " " + AuthManager.getInstance().getLastName());
        }
    }

    @Override
    public void drawMeSubtitle() {
        if (!AuthManager.getInstance().isAuthorized()) {
            navSubtitle.setText(getString(R.string.feedback_login_text));
        } else if (TextUtils.isEmpty(AuthManager.getInstance().getEmail())) {
            navSubtitle.setText("...");
        } else {
            navSubtitle.setText(AuthManager.getInstance().getEmail());
        }
    }

    @Override
    public void drawMeButton() {
        if (!AuthManager.getInstance().isAuthorized()) {
            ThemeManager.setImageResource(
                    navButton, R.drawable.ic_plus_mini_light, R.drawable.ic_plus_mini_dark);
        } else {
            ThemeManager.setImageResource(
                    navButton, R.drawable.ic_close_mini_light, R.drawable.ic_close_mini_dark);
        }
    }

    // drawer view.

    @Override
    public void touchNavItem(int id) {
        messageManagePresenter.sendMessage(id, null);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setCheckedItem(int id) {
        if (id == R.id.action_home
                || id == R.id.action_following
                || id == R.id.action_multi_filter
                || id == R.id.action_category) {
            nav.setCheckedItem(id);
        }
    }
}