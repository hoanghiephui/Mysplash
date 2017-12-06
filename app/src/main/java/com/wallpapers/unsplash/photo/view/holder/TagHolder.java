package com.wallpapers.unsplash.photo.view.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.basic.Tag;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpapers.unsplash.common.ui.adapter.TagAdapter;
import com.wallpapers.unsplash.common.ui.widget.SwipeSwitchLayout;
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Tag holder.
 * */

public class TagHolder extends PhotoInfoAdapter.ViewHolder {

    @BindView(R.id.item_photo_tag)
    SwipeSwitchLayout.RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;

    public static final int TYPE_TAG = 6;

    public TagHolder(View itemView, BaseActivity a) {
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
        List<Tag> tagList = new ArrayList<>();
        if (photo.categories != null) {
            tagList.addAll(photo.categories);
        }
        if (photo.tags != null) {
            tagList.addAll(photo.tags);
        }
        recyclerView.setAdapter(new TagAdapter(activity, tagList));
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
