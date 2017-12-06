package com.wallpapers.unsplash.main.presenter.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.basic.fragment.MysplashFragment;
import com.wallpapers.unsplash.common.interfaces.model.FragmentManageModel;
import com.wallpapers.unsplash.common.interfaces.presenter.FragmentManagePresenter;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.main.view.fragment.CategoryFragment;
import com.wallpapers.unsplash.main.view.fragment.CollectionFragment;
import com.wallpapers.unsplash.main.view.fragment.FollowingFragment;
import com.wallpapers.unsplash.main.view.fragment.HomeFragment;
import com.wallpapers.unsplash.main.view.fragment.MultiFilterFragment;
import com.wallpapers.unsplash.main.view.fragment.SearchQueryFragment;

import java.util.ArrayList;
import java.util.List;

import static com.wallpapers.unsplash.UnsplashApplication.SEARCH_QUERY_ID;

/**
 * Fragment manage implementor.
 * <p>
 * A {@link FragmentManagePresenter} for
 * {@link com.wallpapers.unsplash.main.view.activity.MainActivity}.
 */

public class FragmentManageImplementor
        implements FragmentManagePresenter {

    private FragmentManageModel model;

    public FragmentManageImplementor(FragmentManageModel model) {
        this.model = model;
    }

    @Override
    public List<MysplashFragment> getFragmentList(BaseActivity a, boolean includeHidden) {
        List<Fragment> fragmentList = a.getSupportFragmentManager().getFragments();
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        List<MysplashFragment> resultList = new ArrayList<>();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (fragmentList.get(i) instanceof MysplashFragment
                    && (includeHidden || !fragmentList.get(i).isHidden())) {
                resultList.add((MysplashFragment) fragmentList.get(i));
            }
        }
        return resultList;
    }

    @Override
    @Nullable
    public MysplashFragment getTopFragment(BaseActivity a) {
        List<MysplashFragment> list = getFragmentList(a, false);
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

        MysplashFragment newF = null;
        MysplashFragment oldF = null;

        List<MysplashFragment> list = getFragmentList(a, true);
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
        MysplashFragment f = buildFragmentByCode(code);
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
            MysplashFragment f = getFragmentList(a, false).get(model.getFragmentCount() - 1);
            model.popFragmentFromList();
            a.getSupportFragmentManager().popBackStack();
            f.setStatusBarStyle(f.needSetDarkStatusBar());
        }
    }
*/

    private void replaceFragment(BaseActivity a, MysplashFragment f) {
        a.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.activity_main_fragment, f)
                .commit();
        DisplayUtils.setStatusBarStyle(a, false);
    }

    private void showAndHideFragment(BaseActivity a, MysplashFragment newF, MysplashFragment oldF) {
        a.getSupportFragmentManager()
                .beginTransaction()
                .remove(oldF)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(newF)
                .commit();
        newF.initStatusBarStyle();
        newF.initNavigationBarStyle();
    }

    private void showAndHideNewFragment(BaseActivity a, MysplashFragment newF, MysplashFragment oldF) {
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

    private MysplashFragment buildFragmentByCode(int code, String title) {
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

    private int getFragmentCode(MysplashFragment f) {
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
