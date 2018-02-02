package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.basic.fragment.BaseFragment;

import java.util.List;

/**
 * Fragment manage presenter.
 * <p>
 * Presenter to manage fragments.
 */

public interface FragmentManagePresenter {

    List<BaseFragment> getFragmentList(BaseActivity a, boolean includeHidden);

    BaseFragment getTopFragment(BaseActivity a);

    void changeFragment(BaseActivity a, int code, String title, boolean isExprore);

    int getId();
}
