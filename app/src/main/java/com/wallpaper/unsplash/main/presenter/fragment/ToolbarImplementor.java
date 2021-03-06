package com.wallpaper.unsplash.main.presenter.fragment;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.interfaces.presenter.ToolbarPresenter;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.basic.fragment.BaseFragment;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.main.view.activity.MainActivity;
import com.wallpaper.unsplash.main.view.fragment.CategoryFragment;
import com.wallpaper.unsplash.main.view.fragment.HomeFragment;

/**
 * Toolbar implementor.
 *
 * A {@link ToolbarPresenter} for the views in {@link com.wallpaper.unsplash.main.view.fragment}.
 *
 * */

public class ToolbarImplementor
        implements ToolbarPresenter {

    @Override
    public void touchNavigatorIcon(BaseActivity a) {
        DrawerLayout drawer = (DrawerLayout) a.findViewById(R.id.activity_main_drawerLayout);
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void touchToolbar(BaseActivity a) {
        BaseFragment fragment = ((MainActivity) a).getTopFragment();
        if (fragment != null) {
            fragment.backToTop();
        }
    }

    @Override
    public boolean touchMenuItem(BaseActivity a, int itemId) {
        MainActivity activity = (MainActivity) a;
        switch (itemId) {
            case R.id.action_search:
                IntentHelper.startSearchActivity(a, null);
                break;

            case R.id.action_filter:
                BaseFragment f = activity.getTopFragment();
                if (f instanceof HomeFragment) {
                    ((HomeFragment) f).showPopup();
                } else if (f instanceof CategoryFragment) {
                    ((CategoryFragment) f).showPopup();
                }
                break;
        }
        return true;
    }
}
