package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.basic.fragment.MysplashFragment;

import java.util.List;

/**
 * Fragment manage presenter.
 *
 * Presenter to manage fragments.
 *
 * */

public interface FragmentManagePresenter {

    List<MysplashFragment> getFragmentList(BaseActivity a, boolean includeHidden);
    MysplashFragment getTopFragment(BaseActivity a);

    void changeFragment(BaseActivity a, int code, String title);

    int getId();
}
