package com.wallpapers.unsplash.common.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.data.entity.unsplash.CategoryItem;

import java.util.List;

/**
 * Created by hoanghiep on 11/27/17.
 */

class CategoryHomeAdapter extends RecyclerView.Adapter {
    private List<CategoryItem> categoryItems;
    private Context context;
    private CallBackAdapter callBackAdapter;

    public CategoryHomeAdapter(Context context, List<CategoryItem> categoryItems, CallBackAdapter callBackAdapter) {
        this.categoryItems = categoryItems;
        this.context = context;
        this.callBackAdapter = callBackAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CategoryViewHolder) {
            ((CategoryViewHolder) holder).titleCategory.setText(categoryItems.get(position).getTitle());
            Glide.with(context)
                    .load(categoryItems.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(((CategoryViewHolder) holder).imgCategory);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBackAdapter != null) {
                        callBackAdapter.onClickItem(categoryItems.get(position).getTitle());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public interface CallBackAdapter {
        void onClickItem(String title);
    }
}
