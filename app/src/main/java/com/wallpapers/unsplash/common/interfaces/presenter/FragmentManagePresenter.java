package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common._basic.fragment.MysplashFragment;

import java.util.List;

/**
 * Fragment manage presenter.
 *
 * Presenter to manage fragments.
 *
 * */

public interface FragmentManagePresenter {

    List<MysplashFragment> getFragmentList(MysplashActivity a, boolean includeHidden);
    MysplashFragment getTopFragment(MysplashActivity a);

    void changeFragment(MysplashActivity a, int code, String title);

    int getId();
}
