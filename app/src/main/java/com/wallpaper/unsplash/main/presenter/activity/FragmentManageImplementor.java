package com.wallpaper.unsplash.main.presenter.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.basic.fragment.BaseFragment;
import com.wallpaper.unsplash.common.interfaces.model.FragmentManageModel;
import com.wallpaper.unsplash.common.interfaces.presenter.FragmentManagePresenter;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.main.view.fragment.CategoryFragment;
import com.wallpaper.unsplash.main.view.fragment.CollectionFragment;
import com.wallpaper.unsplash.main.view.fragment.FollowingFragment;
import com.wallpaper.unsplash.main.view.fragment.HomeFragment;
import com.wallpaper.unsplash.main.view.fragment.MultiFilterFragment;
import com.wallpaper.unsplash.main.view.fragment.SearchQueryFragment;

import java.util.ArrayList;
import java.util.List;

import static com.wallpaper.unsplash.UnsplashApplication.SEARCH_QUERY_ID;

/**
 * Fragment manage implementor.
 * <p>
 * A {@link FragmentManagePresenter} for
 * {@link com.wallpaper.unsplash.main.view.activity.MainActivity}.
 */

public class FragmentManageImplementor
        implements FragmentManagePresenter {

    private FragmentManageModel model;

    public FragmentManageImplementor(FragmentManageModel model) {
        this.model = model;
    }

    @Override
    public List<BaseFragment> getFragmentList(BaseActivity a, boolean includeHidden) {
        List<Fragment> fragmentList = a.getSupportFragmentManager().getFragments();
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        List<BaseFragment> resultList = new ArrayList<>();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (fragmentList.get(i) instanceof BaseFragment
                    && (includeHidden || !fragmentList.get(i).isHidden())) {
                resultList.add((BaseFragment) fragmentList.get(i));
            }
        }
        return resultList;
    }

    @Override
    @Nullable
    public BaseFragment getTopFragment(BaseActivity a) {
        List<BaseFragment> list = getFragmentList(a, false);
        if (list.size() > 0) {
            return list.get(list.size() - 1);
        } else {
            return null;
        }
    }

    @Override
    public void changeFragment(BaseActivity a, int code, String title) {
        int oldCode = model.getId();
        model.setId(code);

        BaseFragment newF = null;
        BaseFragment oldF = null;

        List<BaseFragment> list = getFragmentList(a, true);
        for (int i = 0; i < list.size(); i++) {
            if (getFragmentCode(list.get(i)) == oldCode) {
                oldF = list.get(i);
            }
            if (getFragmentCode(list.get(i)) == code) {
                newF = list.get(i);
            }
            if (newF != null && oldF != null) {
                break;
            }
        }
        if (oldF == null) {
            if (newF == null) {
                newF = buildFragmentByCode(code, title);
            }
            replaceFragment(a, newF);
        } else if (newF == null) {
            newF = buildFragmentByCode(code, title);
            showAndHideNewFragment(a, newF, oldF);
        } else {
            showAndHideFragment(a, newF, oldF);
        }
    }

    @Override
    public int getId() {
        return model.getId();
    }
/*
    @Override
    public void addFragment(BaseActivity a, int code) {
        BaseFragment f = buildFragmentByCode(code);
        model.addFragmentToList(code);

        a.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main_fragment, f)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
        DisplayUtils.initStatusBarAndNotificationBarStyle(a);
    }

    @Override
    public void popFragment(BaseActivity a) {
        if (model.getFragmentCount() > 0) {
            BaseFragment f = getFragmentList(a, false).get(model.getFragmentCount() - 1);
            model.popFragmentFromList();
            a.getSupportFragmentManager().popBackStack();
            f.setStatusBarStyle(f.needSetDarkStatusBar());
        }
    }
*/

    private void replaceFragment(BaseActivity a, BaseFragment f) {
        a.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.activity_main_fragment, f)
                .commit();
        DisplayUtils.setStatusBarStyle(a, false);
    }

    private void showAndHideFragment(BaseActivity a, BaseFragment newF, BaseFragment oldF) {
        a.getSupportFragmentManager()
                .beginTransaction()
                .remove(oldF)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(newF)
                .commit();
        newF.initStatusBarStyle();
        newF.initNavigationBarStyle();
    }

    private void showAndHideNewFragment(BaseActivity a, BaseFragment newF, BaseFragment oldF) {
        a.getSupportFragmentManager()
                .beginTransaction()
                .hide(oldF)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.activity_main_fragment, newF)
                .show(newF)
                .commit();
        DisplayUtils.setStatusBarStyle(a, false);
        DisplayUtils.setNavigationBarStyle(a, false, true);
    }

    private BaseFragment buildFragmentByCode(int code, String title) {
        switch (code) {
            case R.id.action_following:
                return new FollowingFragment();

            case R.id.action_collection:
                return new CollectionFragment();

            case R.id.action_multi_filter:
                return new MultiFilterFragment();

            case R.id.action_category:
                return new CategoryFragment();
            case SEARCH_QUERY_ID:
                return SearchQueryFragment.Companion.newInstance(title);

            default:
                return new HomeFragment();
        }
    }

    private int getFragmentCode(BaseFragment f) {
        if (f instanceof HomeFragment) {
            return R.id.action_home;
        } else if (f instanceof FollowingFragment) {
            return R.id.action_following;
        } else if (f instanceof CollectionFragment) {
            return R.id.action_collection;
        } else if (f instanceof MultiFilterFragment) {
            return R.id.action_multi_filter;
        } else if (f instanceof SearchQueryFragment) {
            return SEARCH_QUERY_ID;
        } else { // CategoryFragment.
            return R.id.action_category;
        }
    }
}
