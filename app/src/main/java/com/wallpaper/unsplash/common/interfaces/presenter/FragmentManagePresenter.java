package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.basic.fragment.BaseFragment;

import java.util.List;

/**
 * Fragment manage presenter.
 *
 * Presenter to manage fragments.
 *
 * */

public interface FragmentManagePresenter {

    List<BaseFragment> getFragmentList(BaseActivity a, boolean includeHidden);
    BaseFragment getTopFragment(BaseActivity a);

    void changeFragment(BaseActivity a, int code, String title);

    int getId();
}
