package com.wallpaper.unsplash.common.basic.fragment;

import android.os.Bundle;

import java.util.List;

/**
 * Loadable fragment.
 * */

public abstract class LoadableFragment<T> extends BaseFragment {

    /**
     * {@link com.wallpaper.unsplash.common.basic.activity.LoadableActivity#loadMoreData(List, int, boolean, Bundle)}.
     * */
    public abstract List<T> loadMoreData(List<T> list, int headIndex, boolean headDirection,
                                         Bundle bundle);

    public abstract Bundle getBundleOfList(Bundle bundle);

    public abstract void updateData(T t);
}
