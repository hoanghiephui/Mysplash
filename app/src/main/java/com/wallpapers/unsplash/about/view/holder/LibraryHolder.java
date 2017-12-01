package com.wallpapers.unsplash.about.view.holder;

import android.view.View;
import android.widget.TextView;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.interfaces.model.AboutModel;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.ui.adapter.AboutAdapter;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.about.model.LibraryObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Library holder.
 *
 * This ViewHolder is used to show library for {@link AboutAdapter}.
 *
 * */

public class LibraryHolder extends AboutAdapter.ViewHolder {

    @BindView(R.id.item_about_library_title)
    TextView title;

    @BindView(R.id.item_about_library_content)
    TextView content;

    private String uri;

    public LibraryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        DisplayUtils.setTypeface(itemView.getContext(), title);
        DisplayUtils.setTypeface(itemView.getContext(), content);
    }

    @Override
    protected void onBindView(MysplashActivity a, AboutModel model) {
        LibraryObject object = (LibraryObject) model;

        title.setText(object.title);
        content.setText(object.subtitle);
        uri = object.uri;
    }

    @Override
    protected void onRecycled() {
        // do nothing.
    }

    @OnClick(R.id.item_about_library_container) void clickItem() {
        IntentHelper.startWebActivity(itemView.getContext(), uri);
    }
}