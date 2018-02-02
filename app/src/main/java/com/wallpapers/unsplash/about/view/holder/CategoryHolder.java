package com.wallpapers.unsplash.about.view.holder;

import android.view.View;
import android.widget.TextView;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.about.model.CategoryObject;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.interfaces.model.AboutModel;
import com.wallpapers.unsplash.common.ui.adapter.AboutAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Category holder.
 * <p>
 * This PhotoViewHolder is used to show category for {@link AboutAdapter}.
 */

public class CategoryHolder extends AboutAdapter.ViewHolder {

    @BindView(R.id.item_about_category_title)
    TextView text;

    public CategoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void onBindView(BaseActivity a, AboutModel model) {
        text.setText(((CategoryObject) model).category);
    }

    @Override
    protected void onRecycled() {
        // do nothing.
    }
}
