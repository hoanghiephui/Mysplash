package com.wallpaper.unsplash.photo.view.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.unsplash.Collection;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.ui.adapter.MoreHorizontalAdapter;
import com.wallpaper.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpaper.unsplash.common.ui.widget.SwipeSwitchLayout;
import com.wallpaper.unsplash.photo.view.activity.PhotoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * More landscape holder.
 * */

public class MoreLandscapeHolder extends PhotoInfoAdapter.ViewHolder {

    @BindView(R.id.item_photo_more_landscape_recyclerView)
    SwipeSwitchLayout.RecyclerView recyclerView;

    public static final int TYPE_MORE_LANDSCAPE = 10;

    public MoreLandscapeHolder(View itemView, BaseActivity a) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        a,
                        LinearLayoutManager.HORIZONTAL,
                        false));
    }

    @Override
    protected void onBindView(PhotoActivity activity, Photo photo) {
        List<Collection> collectionList = new ArrayList<>();
        if (photo.related_collections != null) {
            for (int i = 0; i < photo.related_collections.results.size(); i ++) {
                collectionList.add(photo.related_collections.results.get(i));
            }
        }
        recyclerView.setAdapter(new MoreHorizontalAdapter(activity, collectionList));
    }

    @Override
    protected void onRecycled() {
        // do nothing.
    }

    public void setScrollListener(RecyclerView.OnScrollListener listener) {
        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(listener);
    }

    public void scrollTo(int x, int y) {
        recyclerView.scrollTo(x, y);
    }
}
