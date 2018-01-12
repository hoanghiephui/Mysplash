package com.wallpaper.unsplash.common.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by hoanghiep on 5/25/17.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void onBindView(Context context, List<T> tagList);

    protected abstract void onRecycled();
}
