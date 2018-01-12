package com.wallpaper.unsplash.common.ui.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wallpaper.unsplash.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hoanghiep on 11/27/17.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private Unbinder unbinder;
    @BindView(R.id.imgCategory)
    AppCompatImageView imgCategory;
    @BindView(R.id.titleCategory)
    TextView titleCategory;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        this.unbinder = ButterKnife.bind(this, itemView);
    }

    public void unBinder() {
        unbinder.unbind();
    }
}
